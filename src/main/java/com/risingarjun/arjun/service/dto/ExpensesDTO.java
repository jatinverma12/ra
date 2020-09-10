package com.risingarjun.arjun.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.risingarjun.arjun.domain.enumeration.Mode;
import com.risingarjun.arjun.domain.enumeration.ExpensesType;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Expenses} entity.
 */
public class ExpensesDTO implements Serializable {

    private Long id;

    @NotNull
    private String item;

    @Min(value = 0)
    private Integer quantity;

    @Min(value = 0)
    private Integer rate;

    @Min(value = 0)
    private Integer laborCost;

    private Integer otherExpense;

    @NotNull
    private Integer total;

    private LocalDate date;

    @NotNull
    private String transactionId;

    private Mode expenseMode;

    private ExpensesType type;

    @Lob
    private byte[] bill;

    private String billContentType;
    private String remarks;


    private Long incurredById;

    private String incurredByEmployeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Integer laborCost) {
        this.laborCost = laborCost;
    }

    public Integer getOtherExpense() {
        return otherExpense;
    }

    public void setOtherExpense(Integer otherExpense) {
        this.otherExpense = otherExpense;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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

    public Mode getExpenseMode() {
        return expenseMode;
    }

    public void setExpenseMode(Mode expenseMode) {
        this.expenseMode = expenseMode;
    }

    public ExpensesType getType() {
        return type;
    }

    public void setType(ExpensesType type) {
        this.type = type;
    }

    public byte[] getBill() {
        return bill;
    }

    public void setBill(byte[] bill) {
        this.bill = bill;
    }

    public String getBillContentType() {
        return billContentType;
    }

    public void setBillContentType(String billContentType) {
        this.billContentType = billContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getIncurredById() {
        return incurredById;
    }

    public void setIncurredById(Long employeesId) {
        this.incurredById = employeesId;
    }

    public String getIncurredByEmployeeId() {
        return incurredByEmployeeId;
    }

    public void setIncurredByEmployeeId(String employeesEmployeeId) {
        this.incurredByEmployeeId = employeesEmployeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExpensesDTO expensesDTO = (ExpensesDTO) o;
        if (expensesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), expensesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExpensesDTO{" +
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
            ", remarks='" + getRemarks() + "'" +
            ", incurredBy=" + getIncurredById() +
            ", incurredBy='" + getIncurredByEmployeeId() + "'" +
            "}";
    }
}
