package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.risingarjun.arjun.domain.enumeration.Month;

/**
 * A TeachersShare.
 */
@Entity
@Table(name = "teachers_share")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TeachersShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Max(value = 100)
    @Column(name = "share", nullable = false)
    private Integer share;

    @NotNull
    @Column(name = "planned_classes", nullable = false)
    private Integer plannedClasses;

    @NotNull
    @Column(name = "actual_classes", nullable = false)
    private Integer actualClasses;

    @Column(name = "share_correction")
    private Integer shareCorrection;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Month month;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("teachersShares")
    private Employees teacher;

    @ManyToOne
    @JsonIgnoreProperties("teachersShares")
    private Subjects subject;

    @ManyToOne
    @JsonIgnoreProperties("teachersShares")
    private Courses course;

    @ManyToOne
    @JsonIgnoreProperties("teachersShares")
    private AcademicSessions session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShare() {
        return share;
    }

    public TeachersShare share(Integer share) {
        this.share = share;
        return this;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getPlannedClasses() {
        return plannedClasses;
    }

    public TeachersShare plannedClasses(Integer plannedClasses) {
        this.plannedClasses = plannedClasses;
        return this;
    }

    public void setPlannedClasses(Integer plannedClasses) {
        this.plannedClasses = plannedClasses;
    }

    public Integer getActualClasses() {
        return actualClasses;
    }

    public TeachersShare actualClasses(Integer actualClasses) {
        this.actualClasses = actualClasses;
        return this;
    }

    public void setActualClasses(Integer actualClasses) {
        this.actualClasses = actualClasses;
    }

    public Integer getShareCorrection() {
        return shareCorrection;
    }

    public TeachersShare shareCorrection(Integer shareCorrection) {
        this.shareCorrection = shareCorrection;
        return this;
    }

    public void setShareCorrection(Integer shareCorrection) {
        this.shareCorrection = shareCorrection;
    }

    public Month getMonth() {
        return month;
    }

    public TeachersShare month(Month month) {
        this.month = month;
        return this;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getRemarks() {
        return remarks;
    }

    public TeachersShare remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Employees getTeacher() {
        return teacher;
    }

    public TeachersShare teacher(Employees employees) {
        this.teacher = employees;
        return this;
    }

    public void setTeacher(Employees employees) {
        this.teacher = employees;
    }

    public Subjects getSubject() {
        return subject;
    }

    public TeachersShare subject(Subjects subjects) {
        this.subject = subjects;
        return this;
    }

    public void setSubject(Subjects subjects) {
        this.subject = subjects;
    }

    public Courses getCourse() {
        return course;
    }

    public TeachersShare course(Courses courses) {
        this.course = courses;
        return this;
    }

    public void setCourse(Courses courses) {
        this.course = courses;
    }

    public AcademicSessions getSession() {
        return session;
    }

    public TeachersShare session(AcademicSessions academicSessions) {
        this.session = academicSessions;
        return this;
    }

    public void setSession(AcademicSessions academicSessions) {
        this.session = academicSessions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeachersShare)) {
            return false;
        }
        return id != null && id.equals(((TeachersShare) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TeachersShare{" +
            "id=" + getId() +
            ", share=" + getShare() +
            ", plannedClasses=" + getPlannedClasses() +
            ", actualClasses=" + getActualClasses() +
            ", shareCorrection=" + getShareCorrection() +
            ", month='" + getMonth() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
