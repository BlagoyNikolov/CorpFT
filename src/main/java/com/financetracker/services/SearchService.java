package com.financetracker.services;

import com.financetracker.model.Budget;
import com.financetracker.model.PlannedPayment;
import com.financetracker.model.Transaction;
import com.financetracker.model.User;

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
