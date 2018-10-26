package com.financetracker.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts", uniqueConstraints = @UniqueConstraint(columnNames = {"account_id"}))
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long accountId;

    @NotNull
    @Size(min = 2, max = 45)
    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotNull
    @Min((long) -999999999.9999)
    @Max((long) 999999999.9999)
    @Column(name = "amount")
    private BigDecimal amount;

//    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = User.class)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<Transaction> transactions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<Budget> budgets;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<PlannedPayment> plannedPayments;

    public Account() {
        this.transactions = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.plannedPayments = new ArrayList<>();
    }

    public Account(String name, BigDecimal amount, User user,
                   List<Transaction> transactions, List<Budget> budgets, List<PlannedPayment> plannedPayments) {
        this.name = name;
        this.amount = amount;
//        this.user = user;
        this.transactions = transactions;
        this.budgets = budgets;
        this.plannedPayments = plannedPayments;
    }

    public Account(String name, BigDecimal amount, User user) {
        this(name, amount, user, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public List<Budget> getBudgets() {
        return Collections.unmodifiableList(budgets);
    }

    public List<PlannedPayment> getPlannedPayments() {
        return Collections.unmodifiableList(plannedPayments);
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, name, amount, transactions, budgets, plannedPayments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        if (accountId != other.accountId) {
            return false;
        }
        if (amount == null) {
            if (other.amount != null) {
                return false;
            }
        } else if (!amount.equals(other.amount)) {
            return false;
        }
        if (budgets == null) {
            if (other.budgets != null) {
                return false;
            }
        } else if (!budgets.equals(other.budgets)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (plannedPayments == null) {
            if (other.plannedPayments != null) {
                return false;
            }
        } else if (!plannedPayments.equals(other.plannedPayments)) {
            return false;
        }
        if (transactions == null) {
            if (other.transactions != null) {
                return false;
            }
        } else if (!transactions.equals(other.transactions)) {
            return false;
        }
//        if (user != other.user) {
//            return false;
//        }
        return true;
    }
}
