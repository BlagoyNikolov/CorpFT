package com.financetracker.repositories;

import com.financetracker.entities.Account;
import com.financetracker.entities.Budget;
import com.financetracker.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by blagoy
 */
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByAccount(Account account);

    List<Budget> findByCategory(Category category);

    Set<Budget> findByCategoryAndAccount(Category category, Account account);

//    Set<Budget> findByAccountUser(User user);

    Budget findByBudgetId(long budgetId);

    Set<Budget> findAllByNameIsLike(String keyword);
}
