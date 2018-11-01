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

    @ManyToMany
    @JoinTable(
        name = "budgets_has_transactions",
        joinColumns = @JoinColumn(name = "budget_id", referencedColumnName = "budget_id"),
        inverseJoinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    )
    private Set<Transaction> transactions = new HashSet<Transaction>();

    public Budget() {
    }

    public Budget(long budgetId, String name, BigDecimal initialAmount, BigDecimal amount, LocalDateTime fromDate,
                  LocalDateTime toDate, Account account, Category category, Set<Transaction> transactions) {
        this(name, initialAmount, amount, fromDate, toDate, account, category, transactions);

        this.budgetId = budgetId;
    }

    public Budget(long budgetId, String name, BigDecimal initialAmount, LocalDateTime fromDate, LocalDateTime toDate,
                  Account account, Category category) {
        this(budgetId, name, initialAmount, BigDecimal.valueOf(0), fromDate, toDate, account, category, new HashSet<>());
    }

    public Budget(String name, BigDecimal initialAmount, LocalDateTime fromDate, LocalDateTime toDate,
                  Account account, Category category) {
        this(name, initialAmount, BigDecimal.valueOf(0), fromDate, toDate, account, category, new HashSet<>());
    }

    public Budget(long budgetId, String name, BigDecimal initialAmount, BigDecimal amount, LocalDateTime fromDate,
                  LocalDateTime toDate, Account account, Category category) {
        this(name, initialAmount, amount, fromDate, toDate, account, category, new HashSet<>());

        this.budgetId = budgetId;
    }

    public Budget(String name, BigDecimal initialAmount, BigDecimal amount, LocalDateTime fromDate, LocalDateTime toDate,
                  Account account, Category category, Set<Transaction> transactions) {
        this.name = name;
        this.initialAmount = initialAmount;
        this.amount = amount;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.account = account;
        this.category = category;
        this.transactions = transactions;
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
}
