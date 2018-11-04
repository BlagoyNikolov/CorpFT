package com.financetracker.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "transactions", uniqueConstraints = @UniqueConstraint(columnNames = {"transaction_id"}))
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "transaction_id")
  private long transactionId;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PaymentType type;

  @Size(min = 2, max = 45)
  @Column(name = "description")
  private String description;

  @NotNull
  @Min(1)
  @Max((long) 999999999.9999)
  @Column(name = "amount")
  private BigDecimal amount;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Account.class)
  @JoinColumn(name = "account_id", referencedColumnName = "account_id")
  private Account account;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Category.class)
  @JoinColumn(name = "category_id", referencedColumnName = "category_id")
  private Category category;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Currency.class)
  @JoinColumn(name = "currency_id", referencedColumnName = "currency_id")
  private Currency currency;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Currency.class)
  @JoinColumn(name = "account_currency", referencedColumnName = "currency_id")
  private Currency accountCurrency;

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Column(name = "category_name")
  private String categoryName;

  @Column(name = "date")
  private LocalDateTime date;

  @Column(name = "inserted_by")
  private String insertedBy;

  @Column(name = "account_amount")
  private BigDecimal accountAmount;

  @ManyToMany
  @JoinTable(
      name = "budgets_has_transactions",
      joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id"),
      inverseJoinColumns = @JoinColumn(name = "budget_id", referencedColumnName = "budget_id")
  )
  private Set<Budget> budgets = new HashSet<Budget>();

  public Transaction() {
  }

  public long getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(long transactionId) {
    this.transactionId = transactionId;
  }

  public PaymentType getType() {
    return type;
  }

  public void setType(PaymentType type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Account getAccount() {
    return account;
  }

  public Category getCategory() {
    return category;
  }

  public User getUser() {
    return user;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Set<Budget> getBudgets() {
    return budgets;
  }

  public void setBudgets(Set<Budget> budgets) {
    this.budgets = budgets;
  }

  public String getInsertedBy() {
    return insertedBy;
  }

  public void setInsertedBy(String insertedBy) {
    this.insertedBy = insertedBy;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Currency getAccountCurrency() {
    return accountCurrency;
  }

  public void setAccountCurrency(Currency accountCurrency) {
    this.accountCurrency = accountCurrency;
  }

  public BigDecimal getAccountAmount() {
    return accountAmount;
  }

  public void setAccountAmount(BigDecimal accountAmount) {
    this.accountAmount = accountAmount;
  }

  public static class TransactionBuilder {
    private PaymentType paymentType;
    private String description;
    private BigDecimal amount;
    private Account account;
    private Category category;
    private LocalDateTime date;
    private User user;
    private Currency currency;
    private Currency accountCurrency;
    private BigDecimal accountAmount;
    private String insertedBy;
    private String categoryName;

    public TransactionBuilder() {
    }

    public TransactionBuilder setPaymentType(PaymentType paymentType) {
      this.paymentType = paymentType;
      return this;
    }

    public TransactionBuilder setDescription(String description) {
      this.description = description;
      return this;
    }

    public TransactionBuilder setAmount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public TransactionBuilder setAccount(Account account) {
      this.account = account;
      return this;
    }

    public TransactionBuilder setCategory(Category category) {
      this.category = category;
      return this;
    }

    public TransactionBuilder setDate(LocalDateTime date) {
      this.date = date;
      return this;
    }

    public TransactionBuilder setUser(User user) {
      this.user = user;
      return this;
    }

    public TransactionBuilder setCurrency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public TransactionBuilder setAccountCurrency(Currency accountCurrency) {
      this.accountCurrency = accountCurrency;
      return this;
    }

    public TransactionBuilder setAccountAmount(BigDecimal accountAmount) {
      this.accountAmount = accountAmount;
      return this;
    }

    public TransactionBuilder setInsertedBy(String insertedBy) {
      this.insertedBy = insertedBy;
      return this;
    }

    public TransactionBuilder setCategoryName(String categoryName) {
      this.categoryName = categoryName;
      return this;
    }

    public Transaction build() {
      Transaction trn = new Transaction();
      trn.type = this.paymentType;
      trn.description = this.description;
      trn.amount = this.amount;
      trn.account = this.account;
      trn.category = this.category;
      trn.date = this.date;
      trn.user = this.user;
      trn.currency = this.currency;
      trn.accountCurrency = this.accountCurrency;
      trn.accountAmount = this.accountAmount;
      trn.insertedBy = this.insertedBy;
      trn.categoryName = this.categoryName;
      return trn;
    }
  }
}
