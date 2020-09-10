package com.risingarjun.arjun.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Teachers} entity.
 */
public class TeachersDTO implements Serializable {

    private Long id;


    private Long teacherId;

    private String teacherEmployeeId;

    private Set<SubjectsDTO> subjects = new HashSet<>();

    private Set<CoursesDTO> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long employeesId) {
        this.teacherId = employeesId;
    }

    public String getTeacherEmployeeId() {
        return teacherEmployeeId;
    }

    public void setTeacherEmployeeId(String employeesEmployeeId) {
        this.teacherEmployeeId = employeesEmployeeId;
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

        TeachersDTO teachersDTO = (TeachersDTO) o;
        if (teachersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teachersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeachersDTO{" +
            "id=" + getId() +
            ", teacher=" + getTeacherId() +
            ", teacher='" + getTeacherEmployeeId() + "'" +
            "}";
    }
}
