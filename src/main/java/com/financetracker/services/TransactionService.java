package com.financetracker.services;

import com.financetracker.entities.Account;
import com.financetracker.entities.Budget;
import com.financetracker.entities.Currency;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by blagoy
 */
public interface TransactionService {

  List<Transaction> getAllTransactionsByAccountId(long accountId);

  Transaction getTransactionByTransactionId(long transactionId);

  void insertTransaction(Transaction transaction);

  void addTransaction(User user, String account, String category, String type, LocalDateTime date, String amount,
                       Transaction transaction, String currency);

  void editTransaction(User user, String account, String category, String type, LocalDateTime date, String amount,
                      Transaction transaction, String currency, long transactionId);

  void deleteTransaction(User user, long transactionId);

  boolean existsTransaction(Budget budget);

  boolean isBetweenTwoDates(LocalDateTime date, LocalDateTime from, LocalDateTime to);

  Set<Transaction> getAllTransactionsForBudget(Budget budget);

  TreeMap<Integer, List<Transaction>> getAccountTransactionChunks(Long accountId);

  List<Transaction> getPagingTransactions(Long accountId, int page);

  void insertTransaction(User user, Account accountName);

}
