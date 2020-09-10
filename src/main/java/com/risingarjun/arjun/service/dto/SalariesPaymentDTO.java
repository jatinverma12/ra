package com.risingarjun.arjun.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.risingarjun.arjun.domain.enumeration.Mode;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.SalariesPayment} entity.
 */
public class SalariesPaymentDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer salary;

    @NotNull
    @Min(value = 0)
    private Integer paid;

    @NotNull
    @Min(value = 0)
    private Integer unpaid;

    @NotNull
    private LocalDate date;

    @NotNull
    private String transactionId;

    private Mode paymentMode;

    private String remarks;


    private Long employeeId;

    private String employeeEmployeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Integer getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Integer unpaid) {
        this.unpaid = unpaid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Mode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Mode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeesId) {
        this.employeeId = employeesId;
    }

    public String getEmployeeEmployeeId() {
        return employeeEmployeeId;
    }

    public void setEmployeeEmployeeId(String employeesEmployeeId) {
        this.employeeEmployeeId = employeesEmployeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalariesPaymentDTO salariesPaymentDTO = (SalariesPaymentDTO) o;
        if (salariesPaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salariesPaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalariesPaymentDTO{" +
            "id=" + getId() +
            ", salary=" + getSalary() +
            ", paid=" + getPaid() +
            ", unpaid=" + getUnpaid() +
            ", date='" + getDate() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeEmployeeId() + "'" +
            "}";
    }
}
