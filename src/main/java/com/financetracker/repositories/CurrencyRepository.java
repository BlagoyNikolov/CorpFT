package com.financetracker.repositories;

import com.financetracker.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {

  Currency findByCurrencyId(String currencyId);
}
