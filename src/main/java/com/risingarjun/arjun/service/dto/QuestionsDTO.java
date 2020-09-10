package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.risingarjun.arjun.domain.enumeration.QuestionLevel;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Questions} entity.
 */
public class QuestionsDTO implements Serializable {

    private Long id;

    
    @Lob
    private String question;

    @Lob
    private byte[] diagram;

    private String diagramContentType;
    private String option1;

    private String option2;

    private String option3;

    private String option4;

    @NotNull
    private String answer;

    @NotNull
    private Integer maxMarks;

    private Integer negativeMarks;

    @NotNull
    private QuestionLevel level;


    private Long courseId;

    private String courseCourse;

    private Long subjectId;

    private String subjectSubjectTitle;

    private Long chapterId;

    private String chapterChapterTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public byte[] getDiagram() {
        return diagram;
    }

    public void setDiagram(byte[] diagram) {
        this.diagram = diagram;
    }

    public String getDiagramContentType() {
        return diagramContentType;
    }

    public void setDiagramContentType(String diagramContentType) {
        this.diagramContentType = diagramContentType;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Integer getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(Integer negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
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

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chaptersId) {
        this.chapterId = chaptersId;
    }

    public String getChapterChapterTitle() {
        return chapterChapterTitle;
    }

    public void setChapterChapterTitle(String chaptersChapterTitle) {
        this.chapterChapterTitle = chaptersChapterTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionsDTO questionsDTO = (QuestionsDTO) o;
        if (questionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionsDTO{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", diagram='" + getDiagram() + "'" +
            ", option1='" + getOption1() + "'" +
            ", option2='" + getOption2() + "'" +
            ", option3='" + getOption3() + "'" +
            ", option4='" + getOption4() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", maxMarks=" + getMaxMarks() +
            ", negativeMarks=" + getNegativeMarks() +
            ", level='" + getLevel() + "'" +
            ", course=" + getCourseId() +
            ", course='" + getCourseCourse() + "'" +
            ", subject=" + getSubjectId() +
            ", subject='" + getSubjectSubjectTitle() + "'" +
            ", chapter=" + getChapterId() +
            ", chapter='" + getChapterChapterTitle() + "'" +
            "}";
    }
}
