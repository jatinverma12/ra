package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.risingarjun.arjun.domain.enumeration.Mode;

/**
 * A SalariesPayment.
 */
@Entity
@Table(name = "salaries_payment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SalariesPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "salary", nullable = false)
    private Integer salary;

    @NotNull
    @Min(value = 0)
    @Column(name = "paid", nullable = false)
    private Integer paid;

    @NotNull
    @Min(value = 0)
    @Column(name = "unpaid", nullable = false)
    private Integer unpaid;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private Mode paymentMode;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("salariesPayments")
    private Employees employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalary() {
        return salary;
    }

    public SalariesPayment salary(Integer salary) {
        this.salary = salary;
        return this;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getPaid() {
        return paid;
    }

    public SalariesPayment paid(Integer paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getUnpaid() {
        return unpaid;
    }

    public SalariesPayment unpaid(Integer unpaid) {
        this.unpaid = unpaid;
        return this;
    }

    public void setUnpaid(Integer unpaid) {
        this.unpaid = unpaid;
    }

    public LocalDate getDate() {
        return date;
    }

    public SalariesPayment date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public SalariesPayment transactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Mode getPaymentMode() {
        return paymentMode;
    }

    public SalariesPayment paymentMode(Mode paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public void setPaymentMode(Mode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRemarks() {
        return remarks;
    }

    public SalariesPayment remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Employees getEmployee() {
        return employee;
    }

    public SalariesPayment employee(Employees employees) {
        this.employee = employees;
        return this;
    }

    public void setEmployee(Employees employees) {
        this.employee = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalariesPayment)) {
            return false;
        }
        return id != null && id.equals(((SalariesPayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SalariesPayment{" +
            "id=" + getId() +
            ", salary=" + getSalary() +
            ", paid=" + getPaid() +
            ", unpaid=" + getUnpaid() +
            ", date='" + getDate() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
