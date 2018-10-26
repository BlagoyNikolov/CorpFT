package com.financetracker.services;

import com.financetracker.model.Account;
import com.financetracker.model.Budget;
import com.financetracker.model.Category;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;

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

    List<Budget> getAllBudgetsByAccount(Account account);

    List<Budget> getAllBudgetsByCategory(Category category);

    void insertBudget(Budget budget);

    void updateBudget(Budget budget);

    void postBudget(Budget budget, User user, Account account, Category category, String date);

    void postEditBudget(Long budgetId, Budget budget, User user, Budget oldBudget, Account acc,
                        Category category, String date);

    void deleteBudget(Budget budget);

    boolean existsBudget(LocalDateTime date, Category category, Account account);

    Map<Budget, BigDecimal> getBudgets(User user);

    TreeSet<Transaction> getBudgetTransactions(Long budgetId);

    Set<Budget> getAllBudgetsByDateCategoryAndAccount(LocalDateTime date, Category category, Account account);

    boolean isBetweenTwoDates(LocalDateTime date, LocalDateTime from, LocalDateTime to);

    Set<Budget> getAllBudgetsByUser(User user);

    Budget getBudgetByBudgetId(long budgetId);

    List<Transaction> getPagingTransactions(Long budgetId, int page);
}
