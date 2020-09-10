package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Discounts.
 */
@Entity
@Table(name = "discounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Discounts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_2")
    private Integer subject2;

    @Column(name = "subject_3")
    private Integer subject3;

    @Column(name = "subject_4")
    private Integer subject4;

    @Column(name = "subject_5")
    private Integer subject5;

    @Column(name = "subject_6")
    private Integer subject6;

    @Column(name = "subject_7")
    private Integer subject7;

    @Column(name = "subject_8")
    private Integer subject8;

    @Column(name = "quarterly")
    private Integer quarterly;

    @Column(name = "half_yearly")
    private Integer halfYearly;

    @Column(name = "annually")
    private Integer annually;

    @Column(name = "sibling")
    private Integer sibling;

    @Column(name = "referral")
    private Integer referral;

    @ManyToOne
    @JsonIgnoreProperties("discounts")
    private AcademicSessions session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSubject2() {
        return subject2;
    }

    public Discounts subject2(Integer subject2) {
        this.subject2 = subject2;
        return this;
    }

    public void setSubject2(Integer subject2) {
        this.subject2 = subject2;
    }

    public Integer getSubject3() {
        return subject3;
    }

    public Discounts subject3(Integer subject3) {
        this.subject3 = subject3;
        return this;
    }

    public void setSubject3(Integer subject3) {
        this.subject3 = subject3;
    }

    public Integer getSubject4() {
        return subject4;
    }

    public Discounts subject4(Integer subject4) {
        this.subject4 = subject4;
        return this;
    }

    public void setSubject4(Integer subject4) {
        this.subject4 = subject4;
    }

    public Integer getSubject5() {
        return subject5;
    }

    public Discounts subject5(Integer subject5) {
        this.subject5 = subject5;
        return this;
    }

    public void setSubject5(Integer subject5) {
        this.subject5 = subject5;
    }

    public Integer getSubject6() {
        return subject6;
    }

    public Discounts subject6(Integer subject6) {
        this.subject6 = subject6;
        return this;
    }

    public void setSubject6(Integer subject6) {
        this.subject6 = subject6;
    }

    public Integer getSubject7() {
        return subject7;
    }

    public Discounts subject7(Integer subject7) {
        this.subject7 = subject7;
        return this;
    }

    public void setSubject7(Integer subject7) {
        this.subject7 = subject7;
    }

    public Integer getSubject8() {
        return subject8;
    }

    public Discounts subject8(Integer subject8) {
        this.subject8 = subject8;
        return this;
    }

    public void setSubject8(Integer subject8) {
        this.subject8 = subject8;
    }

    public Integer getQuarterly() {
        return quarterly;
    }

    public Discounts quarterly(Integer quarterly) {
        this.quarterly = quarterly;
        return this;
    }

    public void setQuarterly(Integer quarterly) {
        this.quarterly = quarterly;
    }

    public Integer getHalfYearly() {
        return halfYearly;
    }

    public Discounts halfYearly(Integer halfYearly) {
        this.halfYearly = halfYearly;
        return this;
    }

    public void setHalfYearly(Integer halfYearly) {
        this.halfYearly = halfYearly;
    }

    public Integer getAnnually() {
        return annually;
    }

    public Discounts annually(Integer annually) {
        this.annually = annually;
        return this;
    }

    public void setAnnually(Integer annually) {
        this.annually = annually;
    }

    public Integer getSibling() {
        return sibling;
    }

    public Discounts sibling(Integer sibling) {
        this.sibling = sibling;
        return this;
    }

    public void setSibling(Integer sibling) {
        this.sibling = sibling;
    }

    public Integer getReferral() {
        return referral;
    }

    public Discounts referral(Integer referral) {
        this.referral = referral;
        return this;
    }

    public void setReferral(Integer referral) {
        this.referral = referral;
    }

    public AcademicSessions getSession() {
        return session;
    }

    public Discounts session(AcademicSessions academicSessions) {
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
        if (!(o instanceof Discounts)) {
            return false;
        }
        return id != null && id.equals(((Discounts) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Discounts{" +
            "id=" + getId() +
            ", subject2=" + getSubject2() +
            ", subject3=" + getSubject3() +
            ", subject4=" + getSubject4() +
            ", subject5=" + getSubject5() +
            ", subject6=" + getSubject6() +
            ", subject7=" + getSubject7() +
            ", subject8=" + getSubject8() +
            ", quarterly=" + getQuarterly() +
            ", halfYearly=" + getHalfYearly() +
            ", annually=" + getAnnually() +
            ", sibling=" + getSibling() +
            ", referral=" + getReferral() +
            "}";
    }
}
