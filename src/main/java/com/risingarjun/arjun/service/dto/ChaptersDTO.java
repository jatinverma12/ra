package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Chapters} entity.
 */
public class ChaptersDTO implements Serializable {

    private Long id;

    @NotNull
    private String chapterId;

    private String chapterTitle;


    private Long courseId;

    private String courseCourse;

    private Long subjectId;

    private String subjectSubjectTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChaptersDTO chaptersDTO = (ChaptersDTO) o;
        if (chaptersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chaptersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChaptersDTO{" +
            "id=" + getId() +
            ", chapterId='" + getChapterId() + "'" +
            ", chapterTitle='" + getChapterTitle() + "'" +
            ", course=" + getCourseId() +
            ", course='" + getCourseCourse() + "'" +
            ", subject=" + getSubjectId() +
            ", subject='" + getSubjectSubjectTitle() + "'" +
            "}";
    }
}
