package com.financetracker.util;

import com.financetracker.entities.Transaction;

import java.util.Comparator;

public class TransactionComparator implements Comparator<Transaction> {
  @Override
  public int compare(Transaction t1, Transaction t2) {
    if (t2.getDate().compareTo(t1.getDate()) == 0) {
      return Long.compare(t2.getTransactionId(), t1.getTransactionId());
    }
    return t2.getDate().compareTo(t1.getDate());
  }
}
