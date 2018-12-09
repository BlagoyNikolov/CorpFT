package com.financetracker.repositories;

import com.financetracker.entities.Account;
import com.financetracker.entities.Budget;
import com.financetracker.entities.Category;
import com.financetracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by blagoy
 */
public interface BudgetRepository extends JpaRepository<Budget, Long> {

  Set<Budget> findByCategoryAndAccount(Category category, Account account);

  List<Budget> findByUserUserId(Long userId);

  Budget findByBudgetId(long budgetId);
}
