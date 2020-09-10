package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.risingarjun.arjun.domain.enumeration.Month;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.TeachersShare} entity.
 */
public class TeachersShareDTO implements Serializable {

    private Long id;

    @NotNull
    @Max(value = 100)
    private Integer share;

    @NotNull
    private Integer plannedClasses;

    @NotNull
    private Integer actualClasses;

    private Integer shareCorrection;

    private Month month;

    private String remarks;


    private Long teacherId;

    private String teacherEmployeeId;

    private Long subjectId;

    private String subjectSubjectTitle;

    private Long courseId;

    private String courseCourse;

    private Long sessionId;

    private String sessionAcadSessionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getPlannedClasses() {
        return plannedClasses;
    }

    public void setPlannedClasses(Integer plannedClasses) {
        this.plannedClasses = plannedClasses;
    }

    public Integer getActualClasses() {
        return actualClasses;
    }

    public void setActualClasses(Integer actualClasses) {
        this.actualClasses = actualClasses;
    }

    public Integer getShareCorrection() {
        return shareCorrection;
    }

    public void setShareCorrection(Integer shareCorrection) {
        this.shareCorrection = shareCorrection;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectsId) {
        this.subjectId = subjectsId;
    }

    public String getSubjectSubjectTitle() {
        return subjectSubjectTitle;
    }

    public void setSubjectSubjectTitle(String subjectsSubjectTitle) {
        this.subjectSubjectTitle = subjectsSubjectTitle;
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

    public String getSessionAcadSessionId() {
        return sessionAcadSessionId;
    }

    public void setSessionAcadSessionId(String academicSessionsAcadSessionId) {
        this.sessionAcadSessionId = academicSessionsAcadSessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TeachersShareDTO teachersShareDTO = (TeachersShareDTO) o;
        if (teachersShareDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teachersShareDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeachersShareDTO{" +
            "id=" + getId() +
            ", share=" + getShare() +
            ", plannedClasses=" + getPlannedClasses() +
            ", actualClasses=" + getActualClasses() +
            ", shareCorrection=" + getShareCorrection() +
            ", month='" + getMonth() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", teacher=" + getTeacherId() +
            ", teacher='" + getTeacherEmployeeId() + "'" +
            ", subject=" + getSubjectId() +
            ", subject='" + getSubjectSubjectTitle() + "'" +
            ", course=" + getCourseId() +
            ", course='" + getCourseCourse() + "'" +
            ", session=" + getSessionId() +
            ", session='" + getSessionAcadSessionId() + "'" +
            "}";
    }
}
