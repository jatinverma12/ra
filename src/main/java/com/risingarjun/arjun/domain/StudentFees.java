package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.risingarjun.arjun.domain.enumeration.Month;

/**
 * A StudentFees.
 */
@Entity
@Table(name = "student_fees")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentFees implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fee")
    private Integer fee;

    @Column(name = "fee_correction")
    private Integer feeCorrection;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Month month;

    @Column(name = "fee_status")
    private Boolean feeStatus;

    @Column(name = "remarks")
    private String remarks;

    @OneToOne
    @JoinColumn(unique = true)
    private Students registrationno;

    @ManyToOne
    @JsonIgnoreProperties("studentFees")
    private Subjects subject;

    @ManyToOne
    @JsonIgnoreProperties("studentFees")
    private AcademicSessions session;

    @ManyToOne
    @JsonIgnoreProperties("studentFees")
    private Employees teacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFee() {
        return fee;
    }

    public StudentFees fee(Integer fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getFeeCorrection() {
        return feeCorrection;
    }

    public StudentFees feeCorrection(Integer feeCorrection) {
        this.feeCorrection = feeCorrection;
        return this;
    }

    public void setFeeCorrection(Integer feeCorrection) {
        this.feeCorrection = feeCorrection;
    }

    public Month getMonth() {
        return month;
    }

    public StudentFees month(Month month) {
        this.month = month;
        return this;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Boolean isFeeStatus() {
        return feeStatus;
    }

    public StudentFees feeStatus(Boolean feeStatus) {
        this.feeStatus = feeStatus;
        return this;
    }

    public void setFeeStatus(Boolean feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public StudentFees remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Students getRegistrationno() {
        return registrationno;
    }

    public StudentFees registrationno(Students students) {
        this.registrationno = students;
        return this;
    }

    public void setRegistrationno(Students students) {
        this.registrationno = students;
    }

    public Subjects getSubject() {
        return subject;
    }

    public StudentFees subject(Subjects subjects) {
        this.subject = subjects;
        return this;
    }

    public void setSubject(Subjects subjects) {
        this.subject = subjects;
    }

    public AcademicSessions getSession() {
        return session;
    }

    public StudentFees session(AcademicSessions academicSessions) {
        this.session = academicSessions;
        return this;
    }

    public void setSession(AcademicSessions academicSessions) {
        this.session = academicSessions;
    }

    public Employees getTeacher() {
        return teacher;
    }

    public StudentFees teacher(Employees employees) {
        this.teacher = employees;
        return this;
    }

    public void setTeacher(Employees employees) {
        this.teacher = employees;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentFees)) {
            return false;
        }
        return id != null && id.equals(((StudentFees) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StudentFees{" +
            "id=" + getId() +
            ", fee=" + getFee() +
            ", feeCorrection=" + getFeeCorrection() +
            ", month='" + getMonth() + "'" +
            ", feeStatus='" + isFeeStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
