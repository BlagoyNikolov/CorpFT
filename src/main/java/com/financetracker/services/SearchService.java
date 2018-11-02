package com.financetracker.services;

import com.financetracker.entities.Budget;
import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Created by blagoy
 */
public interface SearchService {

  Set<Transaction> getAllTransactionsByKeyword(String keyword);

  Set<PlannedPayment> getAllPlannedPaymentsByKeyword(String keyword);

  Map<Budget, BigDecimal> getAllBudgetsByKeywordAndUser(String keyword, User user);

  boolean isKeywordValid(String keyword);
}
