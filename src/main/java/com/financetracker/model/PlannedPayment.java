package com.financetracker.model;

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

    private String categoryName;

    public PlannedPayment() {
    }

    public PlannedPayment(String name, PaymentType paymentType, LocalDateTime fromDate, BigDecimal amount,
                          String description, Account account, Category category) {
        this.name = name;
        this.paymentType = paymentType;
        this.fromDate = fromDate;
        this.amount = amount;
        this.description = description;
        this.account = account;
        this.category = category;
    }

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
}
