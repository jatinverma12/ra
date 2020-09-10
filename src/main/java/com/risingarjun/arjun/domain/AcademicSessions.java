package com.risingarjun.arjun.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AcademicSessions.
 */
@Entity
@Table(name = "academic_sessions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AcademicSessions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "acad_session_id", nullable = false, unique = true)
    private String acadSessionId;

    @NotNull
    @Column(name = "acad_session", nullable = false, unique = true)
    private String acadSession;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcadSessionId() {
        return acadSessionId;
    }

    public AcademicSessions acadSessionId(String acadSessionId) {
        this.acadSessionId = acadSessionId;
        return this;
    }

    public void setAcadSessionId(String acadSessionId) {
        this.acadSessionId = acadSessionId;
    }

    public String getAcadSession() {
        return acadSession;
    }

    public AcademicSessions acadSession(String acadSession) {
        this.acadSession = acadSession;
        return this;
    }

    public void setAcadSession(String acadSession) {
        this.acadSession = acadSession;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AcademicSessions)) {
            return false;
        }
        return id != null && id.equals(((AcademicSessions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AcademicSessions{" +
            "id=" + getId() +
            ", acadSessionId='" + getAcadSessionId() + "'" +
            ", acadSession='" + getAcadSession() + "'" +
            "}";
    }
}
