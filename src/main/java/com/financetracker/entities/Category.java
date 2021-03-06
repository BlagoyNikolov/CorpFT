package com.financetracker.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = {"category_id", "name"}))
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "category_id")
  private long categoryId;

  @NotNull
  @Size(min = 2, max = 45)
  @NotEmpty
  @Pattern(regexp = "[^\\s]+")
  @Column(name = "name")
  private String name;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PaymentType type;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
  private List<Transaction> transactions;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
  private List<Budget> budgets;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
  private List<PlannedPayment> plannedPayments;

  public Category() {
    this.transactions = new ArrayList<>();
    this.budgets = new ArrayList<>();
    this.plannedPayments = new ArrayList<>();
  }

  public long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(long categoryId) {
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PaymentType getType() {
    return type;
  }

  public void setType(PaymentType type) {
    this.type = type;
  }

  public List<Transaction> getTransactions() {
    return Collections.unmodifiableList(transactions);
  }

  public List<Budget> getBudgets() {
    return Collections.unmodifiableList(budgets);
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((budgets == null) ? 0 : budgets.hashCode());
    result = prime * result + (int) (categoryId ^ (categoryId >>> 32));
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((plannedPayments == null) ? 0 : plannedPayments.hashCode());
    result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
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
    Category other = (Category) obj;
    if (budgets == null) {
      if (other.budgets != null) {
        return false;
      }
    } else if (!budgets.equals(other.budgets)) {
      return false;
    }
    if (categoryId != other.categoryId) {
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
    if (type != other.type) {
      return false;
    }
    return true;
  }
}
