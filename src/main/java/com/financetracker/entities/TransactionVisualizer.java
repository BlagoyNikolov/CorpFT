package com.financetracker.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionVisualizer {
  private LocalDate date;
  private BigDecimal amount;
  private BigDecimal eurAmount;

  public TransactionVisualizer(Transaction transaction) {
    if (transaction.getType().equals(PaymentType.EXPENSE)) {
      this.amount = transaction.getAmount().negate();
      this.eurAmount = transaction.getEurAmount().negate();
    } else if (transaction.getType().equals(PaymentType.INCOME)) {
      this.amount = transaction.getAmount();
      this.eurAmount = transaction.getEurAmount();
    }
    this.date = transaction.getDate().toLocalDate();
  }

  public LocalDate getDate() {
    return date;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getEurAmount() {
    return eurAmount;
  }
}
