package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.risingarjun.arjun.domain.enumeration.JobNature;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Employees} entity.
 */
public class EmployeesDTO implements Serializable {

    private Long id;

    @NotNull
    private String employeeId;

    private JobNature jobNature;

    private Boolean bgc;

    @Lob
    private byte[] resume;

    private String resumeContentType;
    @NotNull
    private String pan;

    @NotNull
    private String accountNo;

    @NotNull
    private String bank;

    @NotNull
    private String ifsc;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public JobNature getJobNature() {
        return jobNature;
    }

    public void setJobNature(JobNature jobNature) {
        this.jobNature = jobNature;
    }

    public Boolean isBgc() {
        return bgc;
    }

    public void setBgc(Boolean bgc) {
        this.bgc = bgc;
    }

    public byte[] getResume() {
        return resume;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getResumeContentType() {
        return resumeContentType;
    }

    public void setResumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeesDTO employeesDTO = (EmployeesDTO) o;
        if (employeesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeesDTO{" +
            "id=" + getId() +
            ", employeeId='" + getEmployeeId() + "'" +
            ", jobNature='" + getJobNature() + "'" +
            ", bgc='" + isBgc() + "'" +
            ", resume='" + getResume() + "'" +
            ", pan='" + getPan() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", bank='" + getBank() + "'" +
            ", ifsc='" + getIfsc() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
