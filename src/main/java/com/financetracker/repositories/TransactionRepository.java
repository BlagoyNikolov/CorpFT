package com.financetracker.repositories;

import com.financetracker.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by blagoy
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findByAccountAccountId(long accountId);

  Transaction findByTransactionId(long transactionId);

  List<Transaction> findByCategoryAndAccount(Category category, Account account);

  Set<Transaction> findAllByDescriptionContaining(String keyword);

  List<Transaction> findByUserUserId(long userId);
}
