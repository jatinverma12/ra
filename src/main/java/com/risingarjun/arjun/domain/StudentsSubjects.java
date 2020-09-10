package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.risingarjun.arjun.domain.enumeration.Month;

/**
 * A StudentsSubjects.
 */
@Entity
@Table(name = "students_subjects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudentsSubjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    private Month month;

    @OneToOne
    @JoinColumn(unique = true)
    private Students registrationno;

    @ManyToOne
    @JsonIgnoreProperties("studentsSubjects")
    private AcademicSessions session;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "students_subjects_subjects",
               joinColumns = @JoinColumn(name = "students_subjects_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subjects_id", referencedColumnName = "id"))
    private Set<Subjects> subjects = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "students_subjects_course",
               joinColumns = @JoinColumn(name = "students_subjects_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Courses> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public StudentsSubjects month(Month month) {
        this.month = month;
        return this;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Students getRegistrationno() {
        return registrationno;
    }

    public StudentsSubjects registrationno(Students students) {
        this.registrationno = students;
        return this;
    }

    public void setRegistrationno(Students students) {
        this.registrationno = students;
    }

    public AcademicSessions getSession() {
        return session;
    }

    public StudentsSubjects session(AcademicSessions academicSessions) {
        this.session = academicSessions;
        return this;
    }

    public void setSession(AcademicSessions academicSessions) {
        this.session = academicSessions;
    }

    public Set<Subjects> getSubjects() {
        return subjects;
    }

    public StudentsSubjects subjects(Set<Subjects> subjects) {
        this.subjects = subjects;
        return this;
    }

    public StudentsSubjects addSubjects(Subjects subjects) {
        this.subjects.add(subjects);
        subjects.getStudentsubjects().add(this);
        return this;
    }

    public StudentsSubjects removeSubjects(Subjects subjects) {
        this.subjects.remove(subjects);
        subjects.getStudentsubjects().remove(this);
        return this;
    }

    public void setSubjects(Set<Subjects> subjects) {
        this.subjects = subjects;
    }

    public Set<Courses> getCourses() {
        return courses;
    }

    public StudentsSubjects courses(Set<Courses> courses) {
        this.courses = courses;
        return this;
    }

    public StudentsSubjects addCourse(Courses courses) {
        this.courses.add(courses);
        courses.getStudentsubjects().add(this);
        return this;
    }

    public StudentsSubjects removeCourse(Courses courses) {
        this.courses.remove(courses);
        courses.getStudentsubjects().remove(this);
        return this;
    }

    public void setCourses(Set<Courses> courses) {
        this.courses = courses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudentsSubjects)) {
            return false;
        }
        return id != null && id.equals(((StudentsSubjects) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StudentsSubjects{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            "}";
    }
}
