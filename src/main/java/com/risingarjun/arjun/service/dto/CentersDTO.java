package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.risingarjun.arjun.domain.enumeration.City;
import com.risingarjun.arjun.domain.enumeration.State;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Centers} entity.
 */
public class CentersDTO implements Serializable {

    private Long id;

    @NotNull
    private String centerCode;

    @NotNull
    private String centerTitle;

    private Integer streetNo;

    @NotNull
    private City city;

    @NotNull
    private State state;

    private Integer pincode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public String getCenterTitle() {
        return centerTitle;
    }

    public void setCenterTitle(String centerTitle) {
        this.centerTitle = centerTitle;
    }

    public Integer getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(Integer streetNo) {
        this.streetNo = streetNo;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CentersDTO centersDTO = (CentersDTO) o;
        if (centersDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centersDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentersDTO{" +
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
