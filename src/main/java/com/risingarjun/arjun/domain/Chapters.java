package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Chapters.
 */
@Entity
@Table(name = "chapters")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chapters implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "chapter_id", nullable = false)
    private String chapterId;

    @Column(name = "chapter_title")
    private String chapterTitle;

    @ManyToOne
    @JsonIgnoreProperties("chapters")
    private Courses course;

    @ManyToOne
    @JsonIgnoreProperties("chapters")
    private Subjects subject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public Chapters chapterId(String chapterId) {
        this.chapterId = chapterId;
        return this;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public Chapters chapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
        return this;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public Courses getCourse() {
        return course;
    }

    public Chapters course(Courses courses) {
        this.course = courses;
        return this;
    }

    public void setCourse(Courses courses) {
        this.course = courses;
    }

    public Subjects getSubject() {
        return subject;
    }

    public Chapters subject(Subjects subjects) {
        this.subject = subjects;
        return this;
    }

    public void setSubject(Subjects subjects) {
        this.subject = subjects;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chapters)) {
            return false;
        }
        return id != null && id.equals(((Chapters) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chapters{" +
            "id=" + getId() +
            ", chapterId='" + getChapterId() + "'" +
            ", chapterTitle='" + getChapterTitle() + "'" +
            "}";
    }
}
