package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.risingarjun.arjun.domain.enumeration.Month;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.StudentsSubjects} entity.
 */
public class StudentsSubjectsDTO implements Serializable {

    private Long id;

    @NotNull
    private Month month;


    private Long registrationnoId;

    private String registrationnoStudentRegId;

    private Long sessionId;

    private String sessionAcadSession;

    private Set<SubjectsDTO> subjects = new HashSet<>();

    private Set<CoursesDTO> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Long getRegistrationnoId() {
        return registrationnoId;
    }

    public void setRegistrationnoId(Long studentsId) {
        this.registrationnoId = studentsId;
    }

    public String getRegistrationnoStudentRegId() {
        return registrationnoStudentRegId;
    }

    public void setRegistrationnoStudentRegId(String studentsStudentRegId) {
        this.registrationnoStudentRegId = studentsStudentRegId;
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

    public Set<SubjectsDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<SubjectsDTO> subjects) {
        this.subjects = subjects;
    }

    public Set<CoursesDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CoursesDTO> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentsSubjectsDTO studentsSubjectsDTO = (StudentsSubjectsDTO) o;
        if (studentsSubjectsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentsSubjectsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentsSubjectsDTO{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", registrationno=" + getRegistrationnoId() +
            ", registrationno='" + getRegistrationnoStudentRegId() + "'" +
            ", session=" + getSessionId() +
            ", session='" + getSessionAcadSession() + "'" +
            "}";
    }
}
