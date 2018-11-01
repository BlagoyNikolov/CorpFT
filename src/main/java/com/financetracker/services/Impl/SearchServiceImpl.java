package com.financetracker.services.Impl;

import com.financetracker.entities.Budget;
import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.repositories.PlannedPaymentRepository;
import com.financetracker.repositories.TransactionRepository;
import com.financetracker.services.BudgetService;
import com.financetracker.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
public class SearchServiceImpl implements SearchService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private PlannedPaymentRepository plannedPaymentRepository;

  @Autowired
  private BudgetService budgetService;

  public Set<Transaction> getAllTransactionsByKeyword(String keyword) {
    return transactionRepository.findAllByDescriptionContaining(keyword);
  }

  public Set<PlannedPayment> getAllPlannedPaymentsByKeyword(String keyword) {
    return plannedPaymentRepository.findAllByDescriptionContaining(keyword);
  }

  public Map<Budget, BigDecimal> getAllBudgetsByKeywordAndUser(String keyword, User user) {

    Map<Budget, BigDecimal> results = new TreeMap<>((b1, b2) -> {
      if (b2.getFromDate().compareTo(b1.getFromDate()) == 0) {
        return Long.compare(b2.getBudgetId(), b1.getBudgetId());
      }
      return b2.getFromDate().compareTo(b1.getFromDate());
    });

    Map<Budget, BigDecimal> budgets = budgetService.getBudgets(user);
    for (Map.Entry<Budget, BigDecimal> entry : budgets.entrySet()) {
      Budget budget = entry.getKey();
      if (budget.getName().toLowerCase().contains(keyword.toLowerCase())) {
        results.put(budget, entry.getValue());
      }
    }

    return results;
  }

  public boolean isKeywordValid(String keyword) {
    return !keyword.isEmpty() && keyword != null && keyword.matches("[^\\s]+") && keyword.length() > 2;
  }
}
