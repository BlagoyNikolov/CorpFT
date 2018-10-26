package com.financetracker.services;

import com.financetracker.model.Transaction;
import com.financetracker.model.User;

import java.util.List;
import java.util.TreeSet;

public interface EmployeeService {

  TreeSet<User> getAllEmployees();

  TreeSet<Transaction> getAllTransactionsByUserId(Long userId);

  List<Transaction> getPagingTransactions(Long userId, int page);

  User getEmployeeById(Long userId);
}
