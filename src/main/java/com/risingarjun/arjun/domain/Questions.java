package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.risingarjun.arjun.domain.enumeration.QuestionLevel;

/**
 * A Questions.
 */
@Entity
@Table(name = "questions")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Questions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "question", nullable = false)
    private String question;

    @Lob
    @Column(name = "diagram")
    private byte[] diagram;

    @Column(name = "diagram_content_type")
    private String diagramContentType;

    @Column(name = "option_1")
    private String option1;

    @Column(name = "option_2")
    private String option2;

    @Column(name = "option_3")
    private String option3;

    @Column(name = "option_4")
    private String option4;

    @NotNull
    @Column(name = "answer", nullable = false)
    private String answer;

    @NotNull
    @Column(name = "max_marks", nullable = false)
    private Integer maxMarks;

    @Column(name = "negative_marks")
    private Integer negativeMarks;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private QuestionLevel level;

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private Courses course;

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private Subjects subject;

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private Chapters chapter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public Questions question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public byte[] getDiagram() {
        return diagram;
    }

    public Questions diagram(byte[] diagram) {
        this.diagram = diagram;
        return this;
    }

    public void setDiagram(byte[] diagram) {
        this.diagram = diagram;
    }

    public String getDiagramContentType() {
        return diagramContentType;
    }

    public Questions diagramContentType(String diagramContentType) {
        this.diagramContentType = diagramContentType;
        return this;
    }

    public void setDiagramContentType(String diagramContentType) {
        this.diagramContentType = diagramContentType;
    }

    public String getOption1() {
        return option1;
    }

    public Questions option1(String option1) {
        this.option1 = option1;
        return this;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public Questions option2(String option2) {
        this.option2 = option2;
        return this;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public Questions option3(String option3) {
        this.option3 = option3;
        return this;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public Questions option4(String option4) {
        this.option4 = option4;
        return this;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getAnswer() {
        return answer;
    }

    public Questions answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public Questions maxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
        return this;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Integer getNegativeMarks() {
        return negativeMarks;
    }

    public Questions negativeMarks(Integer negativeMarks) {
        this.negativeMarks = negativeMarks;
        return this;
    }

    public void setNegativeMarks(Integer negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public Questions level(QuestionLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    public Courses getCourse() {
        return course;
    }

    public Questions course(Courses courses) {
        this.course = courses;
        return this;
    }

    public void setCourse(Courses courses) {
        this.course = courses;
    }

    public Subjects getSubject() {
        return subject;
    }

    public Questions subject(Subjects subjects) {
        this.subject = subjects;
        return this;
    }

    public void setSubject(Subjects subjects) {
        this.subject = subjects;
    }

    public Chapters getChapter() {
        return chapter;
    }

    public Questions chapter(Chapters chapters) {
        this.chapter = chapters;
        return this;
    }

    public void setChapter(Chapters chapters) {
        this.chapter = chapters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Questions)) {
            return false;
        }
        return id != null && id.equals(((Questions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Questions{" +
            "id=" + getId() +
            ", question='" + getQuestion() + "'" +
            ", diagram='" + getDiagram() + "'" +
            ", diagramContentType='" + getDiagramContentType() + "'" +
            ", option1='" + getOption1() + "'" +
            ", option2='" + getOption2() + "'" +
            ", option3='" + getOption3() + "'" +
            ", option4='" + getOption4() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", maxMarks=" + getMaxMarks() +
            ", negativeMarks=" + getNegativeMarks() +
            ", level='" + getLevel() + "'" +
            "}";
    }
}
