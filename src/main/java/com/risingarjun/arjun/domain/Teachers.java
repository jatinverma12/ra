package com.risingarjun.arjun.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Teachers.
 */
@Entity
@Table(name = "teachers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Teachers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Employees teacher;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "teachers_subjects",
               joinColumns = @JoinColumn(name = "teachers_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subjects_id", referencedColumnName = "id"))
    private Set<Subjects> subjects = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "teachers_courses",
               joinColumns = @JoinColumn(name = "teachers_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "courses_id", referencedColumnName = "id"))
    private Set<Courses> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employees getTeacher() {
        return teacher;
    }

    public Teachers teacher(Employees employees) {
        this.teacher = employees;
        return this;
    }

    public void setTeacher(Employees employees) {
        this.teacher = employees;
    }

    public Set<Subjects> getSubjects() {
        return subjects;
    }

    public Teachers subjects(Set<Subjects> subjects) {
        this.subjects = subjects;
        return this;
    }

    public Teachers addSubjects(Subjects subjects) {
        this.subjects.add(subjects);
        subjects.getTeachers().add(this);
        return this;
    }

    public Teachers removeSubjects(Subjects subjects) {
        this.subjects.remove(subjects);
        subjects.getTeachers().remove(this);
        return this;
    }

    public void setSubjects(Set<Subjects> subjects) {
        this.subjects = subjects;
    }

    public Set<Courses> getCourses() {
        return courses;
    }

    public Teachers courses(Set<Courses> courses) {
        this.courses = courses;
        return this;
    }

    public Teachers addCourses(Courses courses) {
        this.courses.add(courses);
        courses.getTeachers().add(this);
        return this;
    }

    public Teachers removeCourses(Courses courses) {
        this.courses.remove(courses);
        courses.getTeachers().remove(this);
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
        if (!(o instanceof Teachers)) {
            return false;
        }
        return id != null && id.equals(((Teachers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Teachers{" +
            "id=" + getId() +
            "}";
    }
}
