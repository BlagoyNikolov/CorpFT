package com.financetracker.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "budgets", uniqueConstraints = @UniqueConstraint(columnNames = {"budget_id"}))
public class Budget {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "budget_id")
  private long budgetId;

  @NotNull
  @Size(min = 2, max = 45)
  @NotEmpty
  @Column(name = "name")
  private String name;

  @NotNull
  @Min(1)
  @Max((long) 999999999.9999)
  @Column(name = "initial_amount")
  private BigDecimal initialAmount;

  @Column(name = "amount")
  private BigDecimal amount;

  @Column(name = "from_date")
  private LocalDateTime fromDate;

  @Column(name = "to_date")
  private LocalDateTime toDate;

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

  @Column(name = "inserted_by")
  private String insertedBy;

  @ManyToMany
  @JoinTable(
      name = "budgets_has_transactions",
      joinColumns = @JoinColumn(name = "budget_id", referencedColumnName = "budget_id"),
      inverseJoinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
  )
  private Set<Transaction> transactions = new HashSet<Transaction>();

  public Budget() {
  }

  public long getBudgetId() {
    return budgetId;
  }

  public void setBudgetId(long budgetId) {
    this.budgetId = budgetId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name.trim();
  }

  public BigDecimal getInitialAmount() {
    return initialAmount;
  }

  public void setInitialAmount(BigDecimal initialAmount) {
    this.initialAmount = initialAmount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getFromDate() {
    return fromDate;
  }

  public void setFromDate(LocalDateTime fromDate) {
    this.fromDate = fromDate;
  }

  public LocalDateTime getToDate() {
    return toDate;
  }

  public void setToDate(LocalDateTime toDate) {
    this.toDate = toDate;
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
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

  public Set<Transaction> getTransactions() {
    return Collections.unmodifiableSet(transactions);
  }

  public void setTransactions(Set<Transaction> transactions) {
    this.transactions = transactions;
  }

  public void addTransaction(Transaction transaction) {
    this.transactions.add(transaction);
  }

  public void removeTransactions() {
    this.transactions.clear();
  }

  public static class BudgetBuilder {
    private long budgetId;
    private String name;
    private BigDecimal initialAmount;
    private BigDecimal amount;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private Account account;
    private Category category;
    private Currency currency;
    private User user;
    private String insertedBy;

    public BudgetBuilder() {
    }

    public BudgetBuilder setBudgetId(long budgetId) {
      this.budgetId = budgetId;
      return this;
    }

    public BudgetBuilder setName(String name) {
      this.name = name;
      return this;
    }

    public BudgetBuilder setInitialAmount(BigDecimal initialAmount) {
      this.initialAmount = initialAmount;
      return this;
    }

    public BudgetBuilder setAmount(BigDecimal amount) {
      this.amount = amount;
      return this;
    }

    public BudgetBuilder setFromDate(LocalDateTime fromDate) {
      this.fromDate = fromDate;
      return this;
    }

    public BudgetBuilder setToDate(LocalDateTime toDate) {
      this.toDate = toDate;
      return this;
    }

    public BudgetBuilder setAccount(Account account) {
      this.account = account;
      return this;
    }

    public BudgetBuilder setCategory(Category category) {
      this.category = category;
      return this;
    }

    public BudgetBuilder setCurrency(Currency currency) {
      this.currency = currency;
      return this;
    }

    public BudgetBuilder setUser(User user) {
      this.user = user;
      return this;
    }

    public BudgetBuilder setInsertedBy(String insertedBy) {
      this.insertedBy = insertedBy;
      return this;
    }

    public Budget build() {
      Budget budget = new Budget();
      budget.budgetId = this.budgetId;
      budget.name = this.name;
      budget.initialAmount = this.initialAmount;
      budget.amount = this.amount;
      budget.fromDate = this.fromDate;
      budget.toDate = this.toDate;
      budget.account = this.account;
      budget.category = this.category;
      budget.currency = this.currency;
      budget.user = this.user;
      budget.insertedBy = this.insertedBy;
      return budget;
    }
  }
}
