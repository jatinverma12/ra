package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Subjects.
 */
@Entity
@Table(name = "subjects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "subject_code", nullable = false, unique = true)
    private String subjectCode;

    @NotNull
    @Column(name = "subject_title", nullable = false, unique = true)
    private String subjectTitle;

    @ManyToMany(mappedBy = "subjects")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<StudentsSubjects> studentsubjects = new HashSet<>();

    @ManyToMany(mappedBy = "subjects")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Teachers> teachers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public Subjects subjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        return this;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public Subjects subjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
        return this;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public Set<StudentsSubjects> getStudentsubjects() {
        return studentsubjects;
    }

    public Subjects studentsubjects(Set<StudentsSubjects> studentsSubjects) {
        this.studentsubjects = studentsSubjects;
        return this;
    }

    public Subjects addStudentsubject(StudentsSubjects studentsSubjects) {
        this.studentsubjects.add(studentsSubjects);
        studentsSubjects.getSubjects().add(this);
        return this;
    }

    public Subjects removeStudentsubject(StudentsSubjects studentsSubjects) {
        this.studentsubjects.remove(studentsSubjects);
        studentsSubjects.getSubjects().remove(this);
        return this;
    }

    public void setStudentsubjects(Set<StudentsSubjects> studentsSubjects) {
        this.studentsubjects = studentsSubjects;
    }

    public Set<Teachers> getTeachers() {
        return teachers;
    }

    public Subjects teachers(Set<Teachers> teachers) {
        this.teachers = teachers;
        return this;
    }

    public Subjects addTeachers(Teachers teachers) {
        this.teachers.add(teachers);
        teachers.getSubjects().add(this);
        return this;
    }

    public Subjects removeTeachers(Teachers teachers) {
        this.teachers.remove(teachers);
        teachers.getSubjects().remove(this);
        return this;
    }

    public void setTeachers(Set<Teachers> teachers) {
        this.teachers = teachers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subjects)) {
            return false;
        }
        return id != null && id.equals(((Subjects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Subjects{" +
            "id=" + getId() +
            ", subjectCode='" + getSubjectCode() + "'" +
            ", subjectTitle='" + getSubjectTitle() + "'" +
            "}";
    }
}
