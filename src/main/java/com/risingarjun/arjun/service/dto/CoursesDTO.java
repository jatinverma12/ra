package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Courses} entity.
 */
public class CoursesDTO implements Serializable {

    private Long id;

    @NotNull
    private String courseId;

    @NotNull
    private String course;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoursesDTO coursesDTO = (CoursesDTO) o;
        if (coursesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coursesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoursesDTO{" +
            "id=" + getId() +
            ", courseId='" + getCourseId() + "'" +
            ", course='" + getCourse() + "'" +
            "}";
    }
}
