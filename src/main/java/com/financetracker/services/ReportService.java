package com.financetracker.services;

import com.financetracker.entities.Account;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by blagoy
 */
public interface ReportService {

    TreeSet<Transaction> getAllReportTransactions(User user, Set<Account> allAccounts);

    TreeSet<Transaction> getFilteredReportTransactions(User user, String categoryName, String type, String accName, String date,
                                                       Set<Account> allAccounts);

    Set<Transaction> getReportTransactions(User user, String type, long categoryId, long accountId,
                                           LocalDateTime from, LocalDateTime to);

    List<Transaction> getPagingTransactions(TreeSet<Transaction> allTransactions, int page);
}
