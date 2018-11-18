package com.financetracker.services.Impl;

import com.financetracker.entities.Account;
import com.financetracker.entities.Budget;
import com.financetracker.entities.Category;
import com.financetracker.entities.Currency;
import com.financetracker.entities.PaymentType;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.repositories.TransactionRepository;
import com.financetracker.services.AccountService;
import com.financetracker.services.BudgetService;
import com.financetracker.services.CategoryService;
import com.financetracker.services.CurrencyService;
import com.financetracker.services.TransactionService;
import com.financetracker.util.PagingUtil;
import com.financetracker.util.TransactionComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

@Service
public class TransactionServiceImpl implements TransactionService {

  public static final String INCOME = "INCOME";
  public static final String EXPENSE = "EXPENSE";
  public static final String DEPOSIT = "DEPOSIT";

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private BudgetService budgetService;

  @Autowired
  private CurrencyService currencyService;

  public List<Transaction> getAllTransactionsByAccountId(long accountId) {
    return transactionRepository.findByAccountAccountId(accountId);
  }

  public Transaction getTransactionByTransactionId(long transactionId) {
    return transactionRepository.findByTransactionId(transactionId);
  }

  @Transactional
  public void addTransaction(User user, String accountName, String category, String type, LocalDateTime date, String amount,
                             Transaction transaction, String currency) {

    Transaction newTransaction = createTransaction(user, accountName, category, type, date, amount, transaction, currency);
    insertNewTransaction(type, newTransaction.getAccount(), newTransaction, newTransaction.getAccountAmount());
  }

  @Transactional
  public void editTransaction(User user, String accountName, String category, String type, LocalDateTime date, String amount,
                             Transaction transaction, String currency, long transactionId) {

    Transaction existingTransaction = createTransaction(user, accountName, category, type, date, amount, transaction, currency);
    existingTransaction.setTransactionId(transactionId);
    updateExistingTransaction(transactionId, existingTransaction.getAccount(), existingTransaction, existingTransaction.getAccountAmount());
  }

  private Transaction createTransaction(User user, String accountName, String category, String type, LocalDateTime date, String amount,
                                        Transaction transaction, String currency) {
    Account persistedAccount = accountService.getAccountByAccountName(accountName);
    Category persistedCategory = categoryService.getCategoryByCategoryName(category);
    Currency persistedCurrency = currencyService.getCurrencyByCurrencyName(currency);
    BigDecimal convertedValue = currencyService.convertToAccountCurrency(persistedCurrency, persistedAccount.getCurrency(), transaction.getAmount());
    String categoryName = categoryService.getCategoryNameByCategoryId(persistedCategory.getCategoryId());
    return new Transaction.TransactionBuilder()
      .setPaymentType(PaymentType.valueOf(type))
      .setDescription(transaction.getDescription())
      .setAmount(BigDecimal.valueOf(Double.valueOf(amount)))
      .setAccount(persistedAccount)
      .setCategory(persistedCategory)
      .setDate(date)
      .setUser(user)
      .setCurrency(transaction.getCurrency())
      .setAccountCurrency(persistedAccount.getCurrency())
      .setAccountAmount(convertedValue)
      .setCategoryName(categoryName)
      .setInsertedBy(user.getFirstName() + " " + user.getLastName())
      .build();
  }

  private void insertNewTransaction(String type, Account acc, Transaction newTransaction, BigDecimal newValue) {
    BigDecimal oldValue = accountService.getAmountByAccountId(newTransaction.getAccount().getAccountId());
    if (type.equals(EXPENSE)) {
      acc.setAmount(oldValue.subtract(newValue));
      accountService.updateAccount(acc);
    } else if (type.equals(INCOME)) {
      acc.setAmount(oldValue.add(newValue));
      accountService.updateAccount(acc);
    }
    insertTransaction(newTransaction);
  }

  private void updateExistingTransaction(long transactionId, Account acc, Transaction newTransaction, BigDecimal newValue) {
    BigDecimal oldValue = accountService.getAmountByAccountId(newTransaction.getAccount().getAccountId());
    Transaction existingTransaction = getTransactionByTransactionId(transactionId);
    if (existingTransaction.getType().equals(PaymentType.EXPENSE)) {
      acc.setAmount(oldValue.add(existingTransaction.getAmount()));
      acc.setAmount(accountService.getAmountByAccountId(acc.getAccountId()).subtract(newValue));
      accountService.updateAccount(acc);
    } else if (existingTransaction.getType().equals(PaymentType.INCOME)) {
      acc.setAmount(oldValue.subtract(existingTransaction.getAmount()));
      acc.setAmount(accountService.getAmountByAccountId(acc.getAccountId()).add(newValue));
      accountService.updateAccount(acc);
    }
    updateTransaction(newTransaction);
  }

  public void insertTransaction(Transaction transaction) {
    transactionRepository.save(transaction);
    addTransactionToExistingBudgets(transaction);
  }

  private void updateTransaction(Transaction transaction) {
    transactionRepository.save(transaction);
  }

  private void addTransactionToExistingBudgets(Transaction transaction) {
    Set<Budget> budgets = budgetService.getAllBudgetsByDateCategoryAndAccount(transaction.getDate(),
        transaction.getCategory(), transaction.getAccount());

    if (budgets.size() != 0 && PaymentType.EXPENSE.equals(transaction.getType())) {
      for (Budget budget : budgets) {
        budget.addTransaction(transaction);
        budget.setAmount(budget.getAmount().add(transaction.getAccountAmount()));
        budgetService.updateBudget(budget);
      }
    }
  }

  @Transactional
  public void deleteTransaction(User user, long transactionId) {
    Transaction transaction = this.getTransactionByTransactionId(transactionId);
    Set<Budget> budgets = budgetService.getAllBudgetsByDateCategoryAndAccount(transaction.getDate(),
        transaction.getCategory(), transaction.getAccount());
    if (budgets.size() != 0 && transaction.getType().equals(PaymentType.EXPENSE)) {
      for (Budget budget : budgets) {
        budget.setAmount(budget.getAmount().subtract(transaction.getAmount()));
        budgetService.updateBudget(budget);
      }
    }
    transactionRepository.delete(transaction);
  }

  public void insertTransaction(User user, Account account) {
    Account acc = accountService.getAccountByAccountName(account.getName());
    Category cat = categoryService.getCategoryByCategoryName(DEPOSIT);
    String description = String.format("Deposit in %s", acc.getName());
    Transaction trn = new Transaction.TransactionBuilder()
      .setPaymentType(PaymentType.INCOME)
      .setDescription(description)
      .setAmount(acc.getAmount())
      .setAccount(acc)
      .setCategory(cat)
      .setDate(LocalDateTime.now())
      .setUser(user)
      .setCurrency(account.getCurrency())
      .setAccountCurrency(account.getCurrency())
      .setAccountAmount(acc.getAmount())
      .setInsertedBy(user.getFirstName() + " " + user.getLastName())
      .setCategoryName(cat.getName())
      .build();
    transactionRepository.save(trn);
  }

  public boolean existsTransaction(Budget budget) {
    LocalDateTime fromDate = budget.getFromDate();
    LocalDateTime toDate = budget.getToDate();
    Category category = budget.getCategory();
    Account account = budget.getAccount();

    List<Transaction> transactions = transactionRepository.findByCategoryAndAccount(category, account);
    for (Transaction transaction : transactions) {
      return PaymentType.EXPENSE.equals(transaction.getType()) && isBetweenTwoDates(transaction.getDate(), fromDate, toDate);
    }
    return false;
  }

  public Set<Transaction> getAllTransactionsForBudget(Budget budget) {
    LocalDateTime fromDate = budget.getFromDate();
    LocalDateTime toDate = budget.getToDate();
    Category category = budget.getCategory();
    Account account = budget.getAccount();

    List<Transaction> transactions = transactionRepository.findByCategoryAndAccount(category, account);
    Set<Transaction> result = new HashSet<>();

    for (Transaction transaction : transactions) {
      if (transaction.getType().equals(PaymentType.EXPENSE) && isBetweenTwoDates(transaction.getDate(), fromDate, toDate)) {
        result.add(transaction);
      }
    }
    return result;
  }

  public boolean isBetweenTwoDates(LocalDateTime date, LocalDateTime from, LocalDateTime to) {
    return !date.isBefore(from) && !date.isAfter(to);
  }

  public TreeMap<Integer, List<Transaction>> getAccountTransactionChunks(Long accountId) {
    TreeMap<Integer, List<Transaction>> result = new TreeMap<>();
    List<Transaction> transactions = transactionRepository.findByAccountAccountId(accountId);
    transactions.sort(new TransactionComparator());

    List<List<Transaction>> chunks = PagingUtil.chunk(transactions, 10);

    int pageAs = 1;
    for (List<Transaction> pageCountents : chunks) {
      result.put(pageAs++, pageCountents);
    }

    return result;
  }

  public List<Transaction> getPagingTransactions(Long accountId, int page) {
    TreeMap<Integer, List<Transaction>> transactions = getAccountTransactionChunks(accountId);
    return transactions.get(page);
  }
}
