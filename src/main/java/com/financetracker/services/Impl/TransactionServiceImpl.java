package com.financetracker.services.Impl;

import com.financetracker.entities.Account;
import com.financetracker.entities.Budget;
import com.financetracker.entities.Category;
import com.financetracker.entities.PaymentType;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.repositories.TransactionRepository;
import com.financetracker.services.AccountService;
import com.financetracker.services.BudgetService;
import com.financetracker.services.CategoryService;
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

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private BudgetService budgetService;

  public List<Transaction> getAllTransactionsByAccountId(long accountId) {
    return transactionRepository.findByAccountAccountId(accountId);
  }

  public List<Transaction> getAllTransactionsByCategoryId(long categoryId) {
    return transactionRepository.findByCategoryCategoryId(categoryId);
  }

  public Transaction getTransactionByTransactionId(long transactionId) {
    return transactionRepository.findByTransactionId(transactionId);
  }

  @Transactional
  public void postTransaction(User user, String accountName, String category, String type, LocalDateTime date, String amount,
                              Transaction transaction, long transactionId) {

    Account acc = accountService.getAccountByAccountName(accountName);
    Category cat = categoryService.getCategoryByCategoryName(category);
    Transaction newTransaction = new Transaction(PaymentType.valueOf(type), transaction.getDescription(),
        BigDecimal.valueOf(Double.valueOf(amount)), acc, cat, date, user);
    if (transactionId != 0) {
      newTransaction.setTransactionId(transactionId);
    }
    BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
    BigDecimal oldValue = accountService.getAmountByAccountId(acc.getAccountId());

    if (transactionId != 0) {
      updateExistingTransaction(user, transactionId, acc, newTransaction, newValue, oldValue);
    } else {
      insertNewTransaction(user, type, acc, newTransaction, newValue, oldValue);
    }
  }

  private void insertNewTransaction(User user, String type, Account acc, Transaction newTransaction,
                                    BigDecimal newValue, BigDecimal oldValue) {
    if (type.equals(EXPENSE)) {
      acc.setAmount(oldValue.subtract(newValue));
      accountService.updateAccount(acc);
    } else if (type.equals(INCOME)) {
      acc.setAmount(oldValue.add(newValue));
      accountService.updateAccount(acc);
    }
    newTransaction.setInsertedBy(user.getFirstName() + " " + user.getLastName());
    insertTransactionAndBudgetCheck(newTransaction);
  }

  private void updateExistingTransaction(User user, long transactionId, Account acc, Transaction newTransaction,
                                         BigDecimal newValue, BigDecimal oldValue) {
    Transaction oldTransacation = getTransactionByTransactionId(transactionId);
    if (oldTransacation.getType().equals(PaymentType.EXPENSE)) {
      acc.setAmount(oldValue.add(oldTransacation.getAmount()));
      acc.setAmount(accountService.getAmountByAccountId(acc.getAccountId()).subtract(newValue));
      accountService.updateAccount(acc);
    } else if (oldTransacation.getType().equals(PaymentType.INCOME)) {
      acc.setAmount(oldValue.subtract(oldTransacation.getAmount()));
      acc.setAmount(accountService.getAmountByAccountId(acc.getAccountId()).add(newValue));
      accountService.updateAccount(acc);
    }
    updateTransaction(newTransaction, user);
  }

  public void insertTransactionAndBudgetCheck(Transaction transaction) {
    Set<Budget> budgets = budgetService.getAllBudgetsByDateCategoryAndAccount(transaction.getDate(),
        transaction.getCategory(), transaction.getAccount());
    transaction.setCategoryName(categoryService.getCategoryNameByCategoryId(transaction.getCategory().getCategoryId()));
    transactionRepository.save(transaction);

    if (budgets.size() != 0 && transaction.getType().equals(PaymentType.EXPENSE)) {
      for (Budget budget : budgets) {
        budget.addTransaction(transaction);
        budget.setAmount(budget.getAmount().add(transaction.getAmount()));
        budgetService.updateBudget(budget);
      }
    }
  }

  private void updateTransaction(Transaction transaction, User user) {
    transaction.setCategoryName(categoryService.getCategoryNameByCategoryId(transaction.getCategory().getCategoryId()));
    transaction.setInsertedBy(user.getFirstName() + " " + user.getLastName());
    transactionRepository.save(transaction);
  }

  @Transactional
  public void deleteTransaction(User user, long transactionId) {
    Transaction transaction = this.getTransactionByTransactionId(transactionId);
    Account account = accountService.getAccountByAccountId(transaction.getAccount().getAccountId());

    BigDecimal newValue = transaction.getAmount();
    BigDecimal oldValue = accountService.getAmountByAccountId(transaction.getAccount().getAccountId());

    if (transaction.getType().equals(PaymentType.EXPENSE)) {
      account.setAmount(oldValue.add(newValue));
      accountService.updateAccount(account);
    } else if (transaction.getType().equals(PaymentType.INCOME)) {
      account.setAmount(oldValue.subtract(newValue));
      accountService.updateAccount(account);
    }

    Set<Budget> budgets = budgetService.getAllBudgetsByDateCategoryAndAccount(transaction.getDate(),
        transaction.getCategory(), transaction.getAccount());
    transactionRepository.delete(transaction);

    if (budgets.size() != 0 && transaction.getType().equals(PaymentType.EXPENSE)) {
      for (Budget budget : budgets) {
        budget.setAmount(budget.getAmount().subtract(transaction.getAmount()));
        budgetService.updateBudget(budget);
      }
    }
  }

  public boolean existsTransaction(Budget budget) {
    LocalDateTime fromDate = budget.getFromDate();
    LocalDateTime toDate = budget.getToDate();
    Category category = budget.getCategory();
    Account account = budget.getAccount();

    List<Transaction> transactions = transactionRepository.findByCategoryAndAccount(category, account);
    for (Transaction transaction : transactions) {
      PaymentType type = transaction.getType();
      LocalDateTime date = transaction.getDate();

      if (type.equals(PaymentType.EXPENSE) && isBetweenTwoDates(date, fromDate, toDate)) {
        return true;
      }
    }
    return false;
  }

  public boolean isBetweenTwoDates(LocalDateTime date, LocalDateTime from, LocalDateTime to) {
    return !date.isBefore(from) && !date.isAfter(to);
  }

  public Set<Transaction> getAllTransactionsForBudget(Budget budget) {
    LocalDateTime fromDate = budget.getFromDate();
    LocalDateTime toDate = budget.getToDate();
    Category category = budget.getCategory();
    Account account = budget.getAccount();

    Set<Transaction> result = new HashSet<>();
    List<Transaction> transactions = transactionRepository.findByCategoryAndAccount(category, account);

    for (Transaction transaction : transactions) {
      PaymentType type = transaction.getType();
      LocalDateTime date = transaction.getDate();

      if (type.equals(PaymentType.EXPENSE) && isBetweenTwoDates(date, fromDate, toDate)) {
        result.add(transaction);
      }
    }
    return result;
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

  @Transactional
  public void insertTransaction(User user, Account account) {
    Account acc = accountService.getAccountByAccountName(account.getName());
    Category cat = categoryService.getCategoryByCategoryName("TRANSFER");
    String description = String.format("First transaction in %s", acc.getName());
    Transaction trn = new Transaction(PaymentType.INCOME, description, acc.getAmount(), acc, cat, LocalDateTime.now(), user);
    trn.setInsertedBy(user.getFirstName() + " " + user.getLastName());
    trn.setCategoryName("Initial Deposit");
    transactionRepository.save(trn);
  }
}
