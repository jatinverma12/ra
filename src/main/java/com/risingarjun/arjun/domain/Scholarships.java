package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Scholarships.
 */
@Entity
@Table(name = "scholarships")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Scholarships implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "min_marks")
    private Integer minMarks;

    @Column(name = "percent")
    private Integer percent;

    @ManyToOne
    @JsonIgnoreProperties("scholarships")
    private AcademicSessions session;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinMarks() {
        return minMarks;
    }

    public Scholarships minMarks(Integer minMarks) {
        this.minMarks = minMarks;
        return this;
    }

    public void setMinMarks(Integer minMarks) {
        this.minMarks = minMarks;
    }

    public Integer getPercent() {
        return percent;
    }

    public Scholarships percent(Integer percent) {
        this.percent = percent;
        return this;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public AcademicSessions getSession() {
        return session;
    }

    public Scholarships session(AcademicSessions academicSessions) {
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
        if (!(o instanceof Scholarships)) {
            return false;
        }
        return id != null && id.equals(((Scholarships) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Scholarships{" +
            "id=" + getId() +
            ", minMarks=" + getMinMarks() +
            ", percent=" + getPercent() +
            "}";
    }
}
