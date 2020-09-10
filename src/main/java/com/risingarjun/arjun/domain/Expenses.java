package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.risingarjun.arjun.domain.enumeration.Mode;

import com.risingarjun.arjun.domain.enumeration.ExpensesType;

/**
 * A Expenses.
 */
@Entity
@Table(name = "expenses")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Expenses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "item", nullable = false)
    private String item;

    @Min(value = 0)
    @Column(name = "quantity")
    private Integer quantity;

    @Min(value = 0)
    @Column(name = "rate")
    private Integer rate;

    @Min(value = 0)
    @Column(name = "labor_cost")
    private Integer laborCost;

    @Column(name = "other_expense")
    private Integer otherExpense;

    @NotNull
    @Column(name = "total", nullable = false)
    private Integer total;

    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "expense_mode")
    private Mode expenseMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ExpensesType type;

    @Lob
    @Column(name = "bill")
    private byte[] bill;

    @Column(name = "bill_content_type")
    private String billContentType;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("expenses")
    private Employees incurredBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public Expenses item(String item) {
        this.item = item;
        return this;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Expenses quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRate() {
        return rate;
    }

    public Expenses rate(Integer rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getLaborCost() {
        return laborCost;
    }

    public Expenses laborCost(Integer laborCost) {
        this.laborCost = laborCost;
        return this;
    }

    public void setLaborCost(Integer laborCost) {
        this.laborCost = laborCost;
    }

    public Integer getOtherExpense() {
        return otherExpense;
    }

    public Expenses otherExpense(Integer otherExpense) {
        this.otherExpense = otherExpense;
        return this;
    }

    public void setOtherExpense(Integer otherExpense) {
        this.otherExpense = otherExpense;
    }

    public Integer getTotal() {
        return total;
    }

    public Expenses total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public Expenses date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Expenses transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Mode getExpenseMode() {
        return expenseMode;
    }

    public Expenses expenseMode(Mode expenseMode) {
        this.expenseMode = expenseMode;
        return this;
    }

    public void setExpenseMode(Mode expenseMode) {
        this.expenseMode = expenseMode;
    }

    public ExpensesType getType() {
        return type;
    }

    public Expenses type(ExpensesType type) {
        this.type = type;
        return this;
    }

    public void setType(ExpensesType type) {
        this.type = type;
    }

    public byte[] getBill() {
        return bill;
    }

    public Expenses bill(byte[] bill) {
        this.bill = bill;
        return this;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }

    public String getBillContentType() {
        return billContentType;
    }

    public Expenses billContentType(String billContentType) {
        this.billContentType = billContentType;
        return this;
    }

    public void setBillContentType(String billContentType) {
        this.billContentType = billContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public Expenses remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Employees getIncurredBy() {
        return incurredBy;
    }

    public Expenses incurredBy(Employees employees) {
        this.incurredBy = employees;
        return this;
    }

    public void setIncurredBy(Employees employees) {
        this.incurredBy = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Expenses)) {
            return false;
        }
        return id != null && id.equals(((Expenses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Expenses{" +
            "id=" + getId() +
            ", item='" + getItem() + "'" +
            ", quantity=" + getQuantity() +
            ", rate=" + getRate() +
            ", laborCost=" + getLaborCost() +
            ", otherExpense=" + getOtherExpense() +
            ", total=" + getTotal() +
            ", date='" + getDate() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", expenseMode='" + getExpenseMode() + "'" +
            ", type='" + getType() + "'" +
            ", bill='" + getBill() + "'" +
            ", billContentType='" + getBillContentType() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
