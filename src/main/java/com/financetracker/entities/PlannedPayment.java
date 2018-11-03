package com.financetracker.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "planned_payments", uniqueConstraints = @UniqueConstraint(columnNames = {"planned_payment_id"}))
public class PlannedPayment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "planned_payment_id")
  private long plannedPaymentId;

  @NotNull
  @Size(min = 2, max = 45)
  @NotEmpty
  @Column(name = "name")
  private String name;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  @Column(name = "from_date")
  private LocalDateTime fromDate;

  @NotNull
  @Min(1)
  @Max((long) 999999999.9999)
  @Column(name = "amount")
  private BigDecimal amount;

  @Size(min = 2, max = 45)
  @Column(name = "description")
  private String description;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Account.class)
  @JoinColumn(name = "account_id", referencedColumnName = "account_id")
  private Account account;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Category.class)
  @JoinColumn(name = "category_id", referencedColumnName = "category_id")
  private Category category;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Currency.class)
  @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
  private Currency currency;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  private String categoryName;

  @Column(name = "inserted_by")
  private String insertedBy;

  @Column(name = "account_amount")
  private BigDecimal accountAmount;

  public long getPlannedPaymentId() {
    return plannedPaymentId;
  }

  public void setPlannedPaymentId(long plannedPaymentId) {
    this.plannedPaymentId = plannedPaymentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name.trim();
  }

  public PaymentType getPaymentType() {
    return paymentType;
  }

  public LocalDateTime getFromDate() {
    return fromDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Account getAccount() {
    return account;
  }

  public Category getCategory() {
    return category;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getInsertedBy() {
    return insertedBy;
  }

  public void setInsertedBy(String insertedBy) {
    this.insertedBy = insertedBy;
  }

  public BigDecimal getAccountAmount() {
    return accountAmount;
  }

  public void setAccountAmount(BigDecimal accountAmount) {
    this.accountAmount = accountAmount;
  }

  public static class PlannedPaymentBuilder {
    private String name;
    private PaymentType paymentType;
    private LocalDateTime date;
    private BigDecimal amount;
    private String description;
    private Account account;
    private Category category;
    private Currency currency;
    private User user;
    private String insertedBy;
    private BigDecimal accountAmount;

    public PlannedPaymentBuilder() {
    }

    public PlannedPaymentBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public PlannedPaymentBuilder setPaymentType(PaymentType paymentType) {
      this.paymentType = paymentType;
      return this;
    }

    public PlannedPaymentBuilder setDate(LocalDateTime date) {
      this.date = date;
      return this;
    }

    public PlannedPaymentBuilder setAmount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public PlannedPaymentBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public PlannedPaymentBuilder setAccount(Account account) {
      this.account = account;
      return this;
    }

    public PlannedPaymentBuilder setCategory(Category category) {
      this.category = category;
      return this;
    }

    public PlannedPaymentBuilder setCurrency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public PlannedPaymentBuilder setUser(User user) {
      this.user = user;
      return this;
    }

    public PlannedPaymentBuilder setInsertedBy(String insertedBy) {
      this.insertedBy = insertedBy;
      return this;
    }

    public PlannedPaymentBuilder setAccountAmount(BigDecimal accountAmount) {
      this.accountAmount = accountAmount;
      return this;
    }

    public PlannedPayment build() {
      PlannedPayment plannedPayment = new PlannedPayment();
      plannedPayment.name = this.name;
      plannedPayment.paymentType = this.paymentType;
      plannedPayment.fromDate = this.date;
      plannedPayment.amount = this.amount;
      plannedPayment.description = this.description;
      plannedPayment.account = this.account;
      plannedPayment.category = this.category;
      plannedPayment.currency = this.currency;
      plannedPayment.user = this.user;
      plannedPayment.insertedBy = this.insertedBy;
      plannedPayment.accountAmount = this.accountAmount;
      return plannedPayment;
    }
  }
}
