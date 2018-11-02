package com.financetracker.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "currencies", uniqueConstraints = @UniqueConstraint(columnNames = {"currency_id"}))
public class Currency {
  @Id
  @Column(name = "currency_id")
  private String currencyId;

  @Column(name = "language")
  private String language;

  @Column(name = "region")
  private String region;

  public String getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(String currencyId) {
    this.currencyId = currencyId;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }
}
