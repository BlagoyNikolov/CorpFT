package com.financetracker.services;

import com.financetracker.entities.Account;
import com.financetracker.entities.Budget;
import com.financetracker.entities.Category;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by blagoy
 */
public interface BudgetService {

  void updateBudget(Budget budget);

  void addBudget(Budget budget, User user, Account account, Category category, String date);

  void postEditBudget(Long budgetId, Budget budget, User user, Budget oldBudget, Account acc,
                      Category category, String date);

  void deleteBudget(Budget budget);

  Map<Budget, BigDecimal> getBudgets(Long employeeId);

  TreeSet<Transaction> getBudgetTransactions(Long budgetId);

  Set<Budget> getAllBudgetsByDateCategoryAndAccount(LocalDateTime date, Category category, Account account);

  boolean isBetweenTwoDates(LocalDateTime date, LocalDateTime from, LocalDateTime to);

  Budget getBudgetByBudgetId(long budgetId);

  List<Transaction> getPagingTransactions(Long budgetId, int page);
}
