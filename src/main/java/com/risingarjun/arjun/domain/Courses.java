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
 * A Courses.
 */
@Entity
@Table(name = "courses")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Courses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "course_id", nullable = false, unique = true)
    private String courseId;

    @NotNull
    @Column(name = "course", nullable = false, unique = true)
    private String course;

    @ManyToMany(mappedBy = "courses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Students> students = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<StudentsSubjects> studentsubjects = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
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

    public String getCourseId() {
        return courseId;
    }

    public Courses courseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourse() {
        return course;
    }

    public Courses course(String course) {
        this.course = course;
        return this;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Set<Students> getStudents() {
        return students;
    }

    public Courses students(Set<Students> students) {
        this.students = students;
        return this;
    }

    public Courses addStudents(Students students) {
        this.students.add(students);
        students.getCourses().add(this);
        return this;
    }

    public Courses removeStudents(Students students) {
        this.students.remove(students);
        students.getCourses().remove(this);
        return this;
    }

    public void setStudents(Set<Students> students) {
        this.students = students;
    }

    public Set<StudentsSubjects> getStudentsubjects() {
        return studentsubjects;
    }

    public Courses studentsubjects(Set<StudentsSubjects> studentsSubjects) {
        this.studentsubjects = studentsSubjects;
        return this;
    }

    public Courses addStudentsubjects(StudentsSubjects studentsSubjects) {
        this.studentsubjects.add(studentsSubjects);
        studentsSubjects.getCourses().add(this);
        return this;
    }

    public Courses removeStudentsubjects(StudentsSubjects studentsSubjects) {
        this.studentsubjects.remove(studentsSubjects);
        studentsSubjects.getCourses().remove(this);
        return this;
    }

    public void setStudentsubjects(Set<StudentsSubjects> studentsSubjects) {
        this.studentsubjects = studentsSubjects;
    }

    public Set<Teachers> getTeachers() {
        return teachers;
    }

    public Courses teachers(Set<Teachers> teachers) {
        this.teachers = teachers;
        return this;
    }

    public Courses addTeachers(Teachers teachers) {
        this.teachers.add(teachers);
        teachers.getCourses().add(this);
        return this;
    }

    public Courses removeTeachers(Teachers teachers) {
        this.teachers.remove(teachers);
        teachers.getCourses().remove(this);
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
        if (!(o instanceof Courses)) {
            return false;
        }
        return id != null && id.equals(((Courses) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Courses{" +
            "id=" + getId() +
            ", courseId='" + getCourseId() + "'" +
            ", course='" + getCourse() + "'" +
            "}";
    }
}
