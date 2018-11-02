package com.financetracker.services.Impl;

import com.financetracker.entities.Currency;
import com.financetracker.repositories.CurrencyRepository;
import com.financetracker.rest.resources.FxResponse;
import com.financetracker.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

  @Autowired
  private CurrencyRepository currencyRepository;

  @Value("${fx.baseUrl}")
  private String fxEndpoint;

  @Override
  public Currency getCurrencyByCurrencyName(String currencyId) {
    return currencyRepository.findByCurrencyId(currencyId);
  }

  @Override
  public List<String> getCurrencyList() {
    return Arrays.asList("BGN", "CAD","CHF", "EUR", "GBP", "JPY", "USD");
  }

  @Override
  public BigDecimal convertToAccountCurrency(Currency from, Currency to, BigDecimal amount) {
    RestTemplate restTemplate = new RestTemplate();
    String currencyPair = from.getCurrencyId() + "_" + to.getCurrencyId();
    String fullUrl = fxEndpoint + currencyPair;
    FxResponse response = restTemplate.getForObject(fullUrl, FxResponse.class);
    BigDecimal fxRate = BigDecimal.valueOf(response.getResults().get(currencyPair).getVal());
    return amount.multiply(fxRate);
  }

}
