package com.financetracker.services;

import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;

import java.util.List;
import java.util.TreeSet;

public interface EmployeeService {

  TreeSet<User> getAllEmployees();

  TreeSet<Transaction> getAllTransactionsByUserId(Long userId);

  TreeSet<PlannedPayment> getAllPlannedPaymentsByUserId(Long userId);

  List<Transaction> getPagingTransactions(Long userId, int page);

  List<PlannedPayment> getPagingPlannedPayments(Long userId, int page);

  User getEmployeeById(Long userId);
}
