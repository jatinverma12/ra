package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.risingarjun.arjun.domain.enumeration.City;

import com.risingarjun.arjun.domain.enumeration.State;

/**
 * A Centers.
 */
@Entity
@Table(name = "centers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Centers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "center_code", nullable = false, unique = true)
    private String centerCode;

    @NotNull
    @Column(name = "center_title", nullable = false, unique = true)
    private String centerTitle;

    @Column(name = "street_no")
    private Integer streetNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "city", nullable = false)
    private City city;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "pincode")
    private Integer pincode;

    @ManyToMany(mappedBy = "centers")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<CenterHead> centerheads = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public Centers centerCode(String centerCode) {
        this.centerCode = centerCode;
        return this;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public Centers centerTitle(String centerTitle) {
        this.centerTitle = centerTitle;
        return this;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle;
    }

    public Integer getStreetNo() {
        return streetNo;
    }

    public Centers streetNo(Integer streetNo) {
        this.streetNo = streetNo;
        return this;
    }

    public void setStreetNo(Integer streetNo) {
        this.streetNo = streetNo;
    }

    public City getCity() {
        return city;
    }

    public Centers city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public Centers state(State state) {
        this.state = state;
        return this;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getPincode() {
        return pincode;
    }

    public Centers pincode(Integer pincode) {
        this.pincode = pincode;
        return this;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public Set<CenterHead> getCenterheads() {
        return centerheads;
    }

    public Centers centerheads(Set<CenterHead> centerHeads) {
        this.centerheads = centerHeads;
        return this;
    }

    public Centers addCenterhead(CenterHead centerHead) {
        this.centerheads.add(centerHead);
        centerHead.getCenters().add(this);
        return this;
    }

    public Centers removeCenterhead(CenterHead centerHead) {
        this.centerheads.remove(centerHead);
        centerHead.getCenters().remove(this);
        return this;
    }

    public void setCenterheads(Set<CenterHead> centerHeads) {
        this.centerheads = centerHeads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Centers)) {
            return false;
        }
        return id != null && id.equals(((Centers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Centers{" +
            "id=" + getId() +
            ", centerCode='" + getCenterCode() + "'" +
            ", centerTitle='" + getCenterTitle() + "'" +
            ", streetNo=" + getStreetNo() +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", pincode=" + getPincode() +
            "}";
    }
}
