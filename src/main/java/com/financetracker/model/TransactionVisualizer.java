package com.financetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionVisualizer {
    private LocalDate date;
    private BigDecimal amount;

    public TransactionVisualizer(Transaction transaction) {
        if (transaction.getType().equals(PaymentType.EXPENSE)) {
            this.amount = transaction.getAmount().negate();
        } else if (transaction.getType().equals(PaymentType.INCOME)) {
            this.amount = transaction.getAmount();
        }
        this.date = transaction.getDate().toLocalDate();
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
