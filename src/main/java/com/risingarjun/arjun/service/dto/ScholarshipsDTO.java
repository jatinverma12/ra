package com.risingarjun.arjun.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Scholarships} entity.
 */
public class ScholarshipsDTO implements Serializable {

    private Long id;

    private Integer minMarks;

    private Integer percent;


    private Long sessionId;

    private String sessionAcadSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinMarks() {
        return minMarks;
    }

    public void setMinMarks(Integer minMarks) {
        this.minMarks = minMarks;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long academicSessionsId) {
        this.sessionId = academicSessionsId;
    }

    public String getSessionAcadSession() {
        return sessionAcadSession;
    }

    public void setSessionAcadSession(String academicSessionsAcadSession) {
        this.sessionAcadSession = academicSessionsAcadSession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScholarshipsDTO scholarshipsDTO = (ScholarshipsDTO) o;
        if (scholarshipsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scholarshipsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScholarshipsDTO{" +
            "id=" + getId() +
            ", minMarks=" + getMinMarks() +
            ", percent=" + getPercent() +
            ", session=" + getSessionId() +
            ", session='" + getSessionAcadSession() + "'" +
            "}";
    }
}
