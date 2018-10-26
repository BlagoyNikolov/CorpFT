package com.financetracker.model;

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

  @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  private String categoryName;

  @Column(name = "date")
  private LocalDateTime date;

  @Column(name = "inserted_by")
  private String insertedBy;

  @ManyToMany
  @JoinTable(
      name = "budgets_has_transactions",
      joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id"),
      inverseJoinColumns = @JoinColumn(name = "budget_id", referencedColumnName = "budget_id")
  )
  private Set<Budget> budgets = new HashSet<Budget>();

  public Transaction() {
  }

  public Transaction(PaymentType type, LocalDateTime date, BigDecimal amount, Account account, Category category, User user) {
    this.type = type;
    this.date = date;
    this.amount = amount;
    this.account = account;
    this.category = category;
    this.user = user;
  }

  public Transaction(PaymentType type, String description, BigDecimal amount,
                     Account account, Category category, LocalDateTime date, User user) {
    this(type, date, amount, account, category, user);
    this.description = description;
  }

  public static Transaction createTransactionByPlannedPayment(PaymentType type, String description, PlannedPayment plannedPayment, User user) {
    return new Transaction(type, description, plannedPayment.getAmount(), plannedPayment.getAccount(),
        plannedPayment.getCategory(), LocalDateTime.now(), user);
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
}
