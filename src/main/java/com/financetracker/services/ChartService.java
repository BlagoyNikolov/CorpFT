package com.financetracker.services;

import com.financetracker.entities.Account;
import com.financetracker.entities.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by blagoy
 */
public interface ChartService {
  TreeSet<Account> getAllAccounts(User user);

  BigDecimal calculateAllBalance(Set<Account> accounts);

  Map<String, BigDecimal> getCashFlowStructure(User user);

  TreeMap<String, BigDecimal> getFilteredCashFlowStructure(User user, LocalDateTime from, LocalDateTime to,
                                                           String type, String account);

  Map<String, BigDecimal> getIncomeVsExpenses(User user);

  Map<String, BigDecimal> getFileteredIncomeVsExpenses(User user, long accountId, LocalDateTime from, LocalDateTime to);

  Map<LocalDate, BigDecimal> getTransactionAmountAndDate(User user);

  Map<LocalDate, BigDecimal> getFilteredTransactionAmountAndDate(User user, long accountId, LocalDateTime from, LocalDateTime to);

  Map<LocalDate, BigDecimal> getGraphData(User user, BigDecimal allBalance);

  Map<LocalDate, BigDecimal> getFilteredGraphData(User user, String date, String account, Set<Account> accounts);
}
