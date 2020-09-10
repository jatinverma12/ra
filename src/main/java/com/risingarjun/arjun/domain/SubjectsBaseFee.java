package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SubjectsBaseFee.
 */
@Entity
@Table(name = "subjects_base_fee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubjectsBaseFee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_fee")
    private Integer baseFee;

    @ManyToOne
    @JsonIgnoreProperties("subjectsBaseFees")
    private Courses course;

    @ManyToOne
    @JsonIgnoreProperties("subjectsBaseFees")
    private AcademicSessions session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBaseFee() {
        return baseFee;
    }

    public SubjectsBaseFee baseFee(Integer baseFee) {
        this.baseFee = baseFee;
        return this;
    }

    public void setBaseFee(Integer baseFee) {
        this.baseFee = baseFee;
    }

    public Courses getCourse() {
        return course;
    }

    public SubjectsBaseFee course(Courses courses) {
        this.course = courses;
        return this;
    }

    public void setCourse(Courses courses) {
        this.course = courses;
    }

    public AcademicSessions getSession() {
        return session;
    }

    public SubjectsBaseFee session(AcademicSessions academicSessions) {
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
        if (!(o instanceof SubjectsBaseFee)) {
            return false;
        }
        return id != null && id.equals(((SubjectsBaseFee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubjectsBaseFee{" +
            "id=" + getId() +
            ", baseFee=" + getBaseFee() +
            "}";
    }
}
