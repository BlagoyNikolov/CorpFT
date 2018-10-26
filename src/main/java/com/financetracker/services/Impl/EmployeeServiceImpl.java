package com.financetracker.services.Impl;

import com.financetracker.model.Transaction;
import com.financetracker.model.User;
import com.financetracker.repositories.TransactionRepository;
import com.financetracker.repositories.UserRepository;
import com.financetracker.services.EmployeeService;
import com.financetracker.util.EmployeeComparator;
import com.financetracker.util.PagingUtil;
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
  public List<Transaction> getPagingTransactions(Long userId, int page) {
    TreeMap<Integer, List<Transaction>> transactions = getAccountTransactionChunks(userId);
    return transactions.get(page);
  }

  @Override
  public User getEmployeeById(Long userId) {
    return userRepository.findByUserId(userId);
  }

  public TreeMap<Integer, List<Transaction>> getAccountTransactionChunks(Long userId) {
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
}
