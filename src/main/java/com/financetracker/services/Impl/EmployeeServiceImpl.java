package com.financetracker.services.Impl;

import com.financetracker.entities.PlannedPayment;
import com.financetracker.entities.Transaction;
import com.financetracker.entities.User;
import com.financetracker.repositories.PlannedPaymentRepository;
import com.financetracker.repositories.TransactionRepository;
import com.financetracker.repositories.UserRepository;
import com.financetracker.services.EmployeeService;
import com.financetracker.util.EmployeeComparator;
import com.financetracker.util.PagingUtil;
import com.financetracker.util.PlannedPaymentComparator;
import com.financetracker.util.TransactionComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private PlannedPaymentRepository plannedPaymentRepository;

  @Override
  public TreeSet<User> getAllEmployees() {
    TreeSet<User> employees = new TreeSet<>(new EmployeeComparator());
    employees.addAll(userRepository.findAll());
    return employees;
  }

  @Override
  public TreeSet<Transaction> getAllTransactionsByUserId(Long userId) {
    TreeSet<Transaction> transactions = new TreeSet<>(new TransactionComparator());
    transactions.addAll(transactionRepository.findByUserUserId(userId));
    return transactions;
  }

  @Override
  public TreeSet<PlannedPayment> getAllPlannedPaymentsByUserId(Long userId) {
    TreeSet<PlannedPayment> plannedPayments = new TreeSet<>(new PlannedPaymentComparator());
    plannedPayments.addAll(plannedPaymentRepository.findByUserUserId(userId));
    return plannedPayments;
  }

  @Override
  public User getEmployeeById(Long userId) {
    return userRepository.findByUserId(userId);
  }

  @Override
  public List<Transaction> getPagingTransactions(Long userId, int page) {
    TreeMap<Integer, List<Transaction>> transactions = getAccountTransactionChunks(userId);
    return transactions.get(page);
  }

  private TreeMap<Integer, List<Transaction>> getAccountTransactionChunks(Long userId) {
    TreeMap<Integer, List<Transaction>> result = new TreeMap<>();
    List<Transaction> transactions = transactionRepository.findByUserUserId(userId);
    transactions.sort(new TransactionComparator());

    List<List<Transaction>> chunks = PagingUtil.chunk(transactions, 10);

    int pageAs = 1;
    for (List<Transaction> pageCountents : chunks) {
      result.put(pageAs++, pageCountents);
    }

    return result;
  }

  @Override
  public List<PlannedPayment> getPagingPlannedPayments(Long userId, int page) {
    TreeMap<Integer, List<PlannedPayment>> plannedPayments = getPlannedPaymentsChunks(userId);
    return plannedPayments.get(page);
  }

  private TreeMap<Integer, List<PlannedPayment>> getPlannedPaymentsChunks(Long userId) {
    TreeMap<Integer, List<PlannedPayment>> result = new TreeMap<>();
    List<PlannedPayment> plannedPayments = plannedPaymentRepository.findByUserUserId(userId);
    plannedPayments.sort(new PlannedPaymentComparator());

    List<List<PlannedPayment>> chunks = PagingUtil.chunk(plannedPayments, 10);

    int pageAs = 1;
    for (List<PlannedPayment> pageCountents : chunks) {
      result.put(pageAs++, pageCountents);
    }

    return result;
  }
}
