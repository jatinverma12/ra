package com.risingarjun.arjun.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.risingarjun.arjun.domain.enumeration.StudentStatus;

import com.risingarjun.arjun.domain.enumeration.LeavingReasons;

import com.risingarjun.arjun.domain.enumeration.InfoSources;

/**
 * A Students.
 */
@Entity
@Table(name = "students")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "student_reg_id", nullable = false, unique = true)
    private String studentRegId;

    @Lob
    @Column(name = "registration_form")
    private byte[] registrationForm;

    @Column(name = "registration_form_content_type")
    private String registrationFormContentType;

    @Column(name = "parent_mob_no_1")
    private String parentMobNo1;

    @Column(name = "parent_mob_no_2")
    private String parentMobNo2;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "parent_email_id")
    private String parentEmailId;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_status")
    private StudentStatus studentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "leaving_reason")
    private LeavingReasons leavingReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "info_source")
    private InfoSources infoSource;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "students_course",
               joinColumns = @JoinColumn(name = "students_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"))
    private Set<Courses> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentRegId() {
        return studentRegId;
    }

    public Students studentRegId(String studentRegId) {
        this.studentRegId = studentRegId;
        return this;
    }

    public void setStudentRegId(String studentRegId) {
        this.studentRegId = studentRegId;
    }

    public byte[] getRegistrationForm() {
        return registrationForm;
    }

    public Students registrationForm(byte[] registrationForm) {
        this.registrationForm = registrationForm;
        return this;
    }

    public void setRegistrationForm(byte[] registrationForm) {
        this.registrationForm = registrationForm;
    }

    public String getRegistrationFormContentType() {
        return registrationFormContentType;
    }

    public Students registrationFormContentType(String registrationFormContentType) {
        this.registrationFormContentType = registrationFormContentType;
        return this;
    }

    public void setRegistrationFormContentType(String registrationFormContentType) {
        this.registrationFormContentType = registrationFormContentType;
    }

    public String getParentMobNo1() {
        return parentMobNo1;
    }

    public Students parentMobNo1(String parentMobNo1) {
        this.parentMobNo1 = parentMobNo1;
        return this;
    }

    public void setParentMobNo1(String parentMobNo1) {
        this.parentMobNo1 = parentMobNo1;
    }

    public String getParentMobNo2() {
        return parentMobNo2;
    }

    public Students parentMobNo2(String parentMobNo2) {
        this.parentMobNo2 = parentMobNo2;
        return this;
    }

    public void setParentMobNo2(String parentMobNo2) {
        this.parentMobNo2 = parentMobNo2;
    }

    public String getParentEmailId() {
        return parentEmailId;
    }

    public Students parentEmailId(String parentEmailId) {
        this.parentEmailId = parentEmailId;
        return this;
    }

    public void setParentEmailId(String parentEmailId) {
        this.parentEmailId = parentEmailId;
    }

    public StudentStatus getStudentStatus() {
        return studentStatus;
    }

    public Students studentStatus(StudentStatus studentStatus) {
        this.studentStatus = studentStatus;
        return this;
    }

    public void setStudentStatus(StudentStatus studentStatus) {
        this.studentStatus = studentStatus;
    }

    public LeavingReasons getLeavingReason() {
        return leavingReason;
    }

    public Students leavingReason(LeavingReasons leavingReason) {
        this.leavingReason = leavingReason;
        return this;
    }

    public void setLeavingReason(LeavingReasons leavingReason) {
        this.leavingReason = leavingReason;
    }

    public InfoSources getInfoSource() {
        return infoSource;
    }

    public Students infoSource(InfoSources infoSource) {
        this.infoSource = infoSource;
        return this;
    }

    public void setInfoSource(InfoSources infoSource) {
        this.infoSource = infoSource;
    }

    public User getUser() {
        return user;
    }

    public Students user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Courses> getCourses() {
        return courses;
    }

    public Students courses(Set<Courses> courses) {
        this.courses = courses;
        return this;
    }

    public Students addCourse(Courses courses) {
        this.courses.add(courses);
        courses.getStudents().add(this);
        return this;
    }

    public Students removeCourse(Courses courses) {
        this.courses.remove(courses);
        courses.getStudents().remove(this);
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
        if (!(o instanceof Students)) {
            return false;
        }
        return id != null && id.equals(((Students) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Students{" +
            "id=" + getId() +
            ", studentRegId='" + getStudentRegId() + "'" +
            ", registrationForm='" + getRegistrationForm() + "'" +
            ", registrationFormContentType='" + getRegistrationFormContentType() + "'" +
            ", parentMobNo1='" + getParentMobNo1() + "'" +
            ", parentMobNo2='" + getParentMobNo2() + "'" +
            ", parentEmailId='" + getParentEmailId() + "'" +
            ", studentStatus='" + getStudentStatus() + "'" +
            ", leavingReason='" + getLeavingReason() + "'" +
            ", infoSource='" + getInfoSource() + "'" +
            "}";
    }
}
