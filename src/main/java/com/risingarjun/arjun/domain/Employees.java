package com.risingarjun.arjun.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.risingarjun.arjun.domain.enumeration.JobNature;

/**
 * A Employees.
 */
@Entity
@Table(name = "employees")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "employee_id", nullable = false, unique = true)
    private String employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_nature")
    private JobNature jobNature;

    @Column(name = "bgc")
    private Boolean bgc;

    @Lob
    @Column(name = "resume")
    private byte[] resume;

    @Column(name = "resume_content_type")
    private String resumeContentType;

    @NotNull
    @Column(name = "pan", nullable = false)
    private String pan;

    @NotNull
    @Column(name = "account_no", nullable = false)
    private String accountNo;

    @NotNull
    @Column(name = "bank", nullable = false)
    private String bank;

    @NotNull
    @Column(name = "ifsc", nullable = false)
    private String ifsc;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Employees employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public JobNature getJobNature() {
        return jobNature;
    }

    public Employees jobNature(JobNature jobNature) {
        this.jobNature = jobNature;
        return this;
    }

    public void setJobNature(JobNature jobNature) {
        this.jobNature = jobNature;
    }

    public Boolean isBgc() {
        return bgc;
    }

    public Employees bgc(Boolean bgc) {
        this.bgc = bgc;
        return this;
    }

    public void setBgc(Boolean bgc) {
        this.bgc = bgc;
    }

    public byte[] getResume() {
        return resume;
    }

    public Employees resume(byte[] resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(byte[] resume) {
        this.resume = resume;
    }

    public String getResumeContentType() {
        return resumeContentType;
    }

    public Employees resumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
        return this;
    }

    public void setResumeContentType(String resumeContentType) {
        this.resumeContentType = resumeContentType;
    }

    public String getPan() {
        return pan;
    }

    public Employees pan(String pan) {
        this.pan = pan;
        return this;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Employees accountNo(String accountNo) {
        this.accountNo = accountNo;
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBank() {
        return bank;
    }

    public Employees bank(String bank) {
        this.bank = bank;
        return this;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getIfsc() {
        return ifsc;
    }

    public Employees ifsc(String ifsc) {
        this.ifsc = ifsc;
        return this;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public User getUser() {
        return user;
    }

    public Employees user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employees)) {
            return false;
        }
        return id != null && id.equals(((Employees) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employees{" +
            "id=" + getId() +
            ", employeeId='" + getEmployeeId() + "'" +
            ", jobNature='" + getJobNature() + "'" +
            ", bgc='" + isBgc() + "'" +
            ", resume='" + getResume() + "'" +
            ", resumeContentType='" + getResumeContentType() + "'" +
            ", pan='" + getPan() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", bank='" + getBank() + "'" +
            ", ifsc='" + getIfsc() + "'" +
            "}";
    }
}
