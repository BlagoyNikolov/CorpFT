package com.financetracker.services.Impl;

import com.financetracker.model.Account;
import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.PaymentType;
import com.financetracker.model.User;
import com.financetracker.repositories.AccountRepository;
import com.financetracker.services.AccountService;
import com.financetracker.services.CategoryService;
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
    //        return accountRepository.findByUser(user);
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
  public void makeTransferToOtherAccount(User user, String inputFromAccount, String inputToAccount, String inputAmount) {
    BigDecimal amount = BigDecimal.valueOf(Double.valueOf(inputAmount));
    Account from = getAccountByAccountName(inputFromAccount);
    Account to = getAccountByAccountName(inputToAccount);

    BigDecimal currentAccountAmount = from.getAmount();
    BigDecimal newCurrentAccountAmount = currentAccountAmount.subtract(amount);
    from.setAmount(newCurrentAccountAmount);
    updateAccount(from);

    BigDecimal otherAccountAmount = to.getAmount();
    BigDecimal newOtherAccountAmount = otherAccountAmount.add(amount);
    to.setAmount(newOtherAccountAmount);
    updateAccount(to);

    Category transferCategory = categoryService.getCategoryByCategoryName(TRANSFER);
    Transaction t1 = new Transaction(PaymentType.EXPENSE, LocalDateTime.now(), amount, from, transferCategory, user);
    t1.setDescription(TRANSFER_TO_ACCOUNT + to.getName());
    t1.setInsertedBy(user.getFirstName() + " " + user.getLastName());
    Transaction t2 = new Transaction(PaymentType.INCOME, LocalDateTime.now(), amount, to, transferCategory, user);
    t2.setDescription(TRANSFER_FROM_ACCOUNT + from.getName());
    t2.setInsertedBy(user.getFirstName() + " " + user.getLastName());

    transactionService.insertTransactionAndBudgetCheck(t1);
    transactionService.insertTransactionAndBudgetCheck(t2);
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
    String balance = NumberFormat.getCurrencyInstance(Locale.US).format(accountBalance);
    return accountBalance.signum() == -1 ? ("-" + balance) : balance;
  }
}
