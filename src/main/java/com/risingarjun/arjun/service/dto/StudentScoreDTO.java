package com.risingarjun.arjun.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.StudentScore} entity.
 */
public class StudentScoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String answer;

    @NotNull
    private Integer score;

    @NotNull
    private LocalDate date;


    private Long studentId;

    private String studentStudentRegId;

    private Long questionIdId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentsId) {
        this.studentId = studentsId;
    }

    public String getStudentStudentRegId() {
        return studentStudentRegId;
    }

    public void setStudentStudentRegId(String studentsStudentRegId) {
        this.studentStudentRegId = studentsStudentRegId;
    }

    public Long getQuestionIdId() {
        return questionIdId;
    }

    public void setQuestionIdId(Long questionsId) {
        this.questionIdId = questionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentScoreDTO studentScoreDTO = (StudentScoreDTO) o;
        if (studentScoreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentScoreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentScoreDTO{" +
            "id=" + getId() +
            ", answer='" + getAnswer() + "'" +
            ", score=" + getScore() +
            ", date='" + getDate() + "'" +
            ", student=" + getStudentId() +
            ", student='" + getStudentStudentRegId() + "'" +
            ", questionId=" + getQuestionIdId() +
            "}";
    }
}
