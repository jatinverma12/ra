package com.risingarjun.arjun.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.SubjectsBaseFee} entity.
 */
public class SubjectsBaseFeeDTO implements Serializable {

    private Long id;

    private Integer baseFee;


    private Long courseId;

    private String courseCourse;

    private Long sessionId;

    private String sessionAcadSession;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBaseFee() {
        return baseFee;
    }

    public void setBaseFee(Integer baseFee) {
        this.baseFee = baseFee;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long coursesId) {
        this.courseId = coursesId;
    }

    public String getCourseCourse() {
        return courseCourse;
    }

    public void setCourseCourse(String coursesCourse) {
        this.courseCourse = coursesCourse;
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

        SubjectsBaseFeeDTO subjectsBaseFeeDTO = (SubjectsBaseFeeDTO) o;
        if (subjectsBaseFeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subjectsBaseFeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubjectsBaseFeeDTO{" +
            "id=" + getId() +
            ", baseFee=" + getBaseFee() +
            ", course=" + getCourseId() +
            ", course='" + getCourseCourse() + "'" +
            ", session=" + getSessionId() +
            ", session='" + getSessionAcadSession() + "'" +
            "}";
    }
}
