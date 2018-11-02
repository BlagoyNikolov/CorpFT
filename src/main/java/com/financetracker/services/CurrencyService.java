package com.financetracker.services;

import com.financetracker.entities.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyService {

  Currency getCurrencyByCurrencyName(String currencyId);

  List<String> getCurrencyList();

  BigDecimal convertToAccountCurrency(Currency persistedCurrency, Currency transactionCurrency, BigDecimal amount);
}
