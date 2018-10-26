package com.financetracker.repositories;

import com.financetracker.model.Account;
import com.financetracker.model.Budget;
import com.financetracker.model.Category;
import com.financetracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
