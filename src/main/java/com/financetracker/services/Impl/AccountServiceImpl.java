package com.financetracker.services.Impl;

import com.financetracker.entities.Account;
import com.financetracker.entities.Category;
import com.financetracker.entities.Currency;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.PaymentType;
import com.financetracker.entities.TransactionVisualizer;
import com.financetracker.entities.User;
import com.financetracker.repositories.AccountRepository;
import com.financetracker.services.AccountService;
import com.financetracker.services.CategoryService;
import com.financetracker.services.CurrencyService;
import com.financetracker.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

  public static final String TRANSFER = "Transfer";
  public static final String TRANSFER_TO_ACCOUNT = "Transfer to account ";
  public static final String TRANSFER_FROM_ACCOUNT = "Transfer from account ";

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private CurrencyService currencyService;

  @Transactional
  public void insertAccount(Account account, User user) {
    if (BigDecimal.ZERO.compareTo(account.getAmount()) <= 0) {
      if (!checkForUniqueness(account)) {
        accountRepository.save(account);
      }
      if (!BigDecimal.ZERO.equals(account.getAmount())) {
        insertTransactionOnAccountCreation(user, account);
      }
    }
  }

  private boolean checkForUniqueness(Account account) {
    return getExistingAccount(account.getName()).isPresent();
  }

  @Transactional
  public void deleteAccount(long accountId, User user) {
    accountRepository.delete(accountId);
  }

  public long getAccountId(User user, String name) {
    Account account = accountRepository.findByName(name);
    return account.getAccountId();
  }

  public Set<Account> getAllAccounts() {
    List<Account> accountList = accountRepository.findAll();
    Set<Account> accountSet = new HashSet<>();
    accountSet.addAll(accountList);
    return accountSet;
  }

  public Account getAccountByAccountId(long accountId) {
    return accountRepository.findByAccountId(accountId);
  }

  public void updateAccount(Account account) {
    accountRepository.save(account);
  }

  @Transactional
  public void makeTransferToOtherAccount(User user, String fromAccount, String toAccount, String inputAmount) {
    BigDecimal eurAmount = BigDecimal.valueOf(Double.valueOf(inputAmount));
    Account from = getAccountByAccountName(fromAccount);
    Account to = getAccountByAccountName(toAccount);
    Currency fromCurrency = from.getCurrency();
    Currency toCurrency = to.getCurrency();
    Currency euro = currencyService.getCurrencyByCurrencyName("EUR");

    BigDecimal fromAccountAmount = from.getAmount();
    BigDecimal withdrawalAmount = currencyService.convertToAccountCurrency(euro, fromCurrency, eurAmount);
    BigDecimal newFromAccountAmount = fromAccountAmount.subtract(withdrawalAmount);
    from.setAmount(newFromAccountAmount);
    updateAccount(from);

    BigDecimal otherAccountAmount = to.getAmount();
    BigDecimal depositAmount = currencyService.convertToAccountCurrency(euro, toCurrency, eurAmount);
    BigDecimal newOtherAccountAmount = otherAccountAmount.add(depositAmount);
    to.setAmount(newOtherAccountAmount);
    updateAccount(to);

    Category transferCategory = categoryService.getCategoryByCategoryName(TRANSFER);
    Transaction t1 = new Transaction.TransactionBuilder()
        .setPaymentType(PaymentType.EXPENSE)
        .setDate(LocalDateTime.now())
        .setAmount(eurAmount)
        .setAccountAmount(withdrawalAmount)
        .setEurAmount(eurAmount)
        .setAccount(from)
        .setCategory(transferCategory)
        .setCategoryName(transferCategory.getName())
        .setUser(user)
        .setCurrency(euro)
        .setAccountCurrency(from.getCurrency())
        .setDescription(TRANSFER_TO_ACCOUNT + to.getName())
        .setInsertedBy(user.getFirstName() + " " + user.getLastName())
        .build();
    Transaction t2 = new Transaction.TransactionBuilder()
        .setPaymentType(PaymentType.INCOME)
        .setDate(LocalDateTime.now())
        .setAmount(eurAmount)
        .setAccountAmount(depositAmount)
        .setEurAmount(eurAmount)
        .setAccount(to)
        .setCategory(transferCategory)
        .setCategoryName(transferCategory.getName())
        .setUser(user)
        .setCurrency(euro)
        .setAccountCurrency(to.getCurrency())
        .setDescription(TRANSFER_FROM_ACCOUNT + from.getName())
        .setInsertedBy(user.getFirstName() + " " + user.getLastName())
        .build();

    transactionService.insertTransaction(t1);
    transactionService.insertTransaction(t2);
  }

  public BigDecimal getAmountByAccountId(long accountId) {
    Account account = getAccountByAccountId(accountId);
    return account.getAmount();
  }

  public String getAccountNameByAccountId(long accountId) {
    Account account = getAccountByAccountId(accountId);
    return account.getName();
  }

  public Account getAccountByAccountName(String name) {
    Optional<Account> existingAccounts = getExistingAccount(name);
    return existingAccounts.isPresent() ? existingAccounts.get() : accountRepository.findByName(name);
  }

  private void insertTransactionOnAccountCreation(User user, Account account) {
    transactionService.insertTransaction(user, account);
  }

  public Optional<Account> getExistingAccount(String name) {
    return accountRepository
        .findAll()
        .stream()
        .filter(account -> name.equals(account.getName()))
        .findAny();
  }

  public String setAccountBalance(Long accountId) {
    BigDecimal accountBalance = getAmountByAccountId(accountId);
    Locale currencyLocale = determineCurrencyLocale(accountId);
    String balance = NumberFormat.getCurrencyInstance(currencyLocale).format(accountBalance);
    return accountBalance.signum() == -1 ? ("-" + balance) : balance;
  }

  @Override
  public BigDecimal calculateAllAccountBalance() {
    return accountRepository
        .findAll()
        .stream()
        .map(account -> calculateAccountAmount(account))
        .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
  }

  private BigDecimal calculateAccountAmount(Account account) {
    return account.getTransactions()
        .stream()
        .map(transaction -> new TransactionVisualizer(transaction))
        .map(TransactionVisualizer::getEurAmount)
        .collect(Collectors.reducing(BigDecimal.ZERO, BigDecimal::add));
  }

  private Locale determineCurrencyLocale(Long accountId) {
    Account accountCurrency = accountRepository.findByAccountId(accountId);
    Currency currency = accountCurrency.getCurrency();
    return new Locale.Builder().setLanguage(currency.getLanguage()).setRegion(currency.getRegion()).build();
  }
}
