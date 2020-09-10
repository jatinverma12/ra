package com.risingarjun.arjun.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.risingarjun.arjun.domain.enumeration.City;
import com.risingarjun.arjun.domain.enumeration.State;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.UserDetails} entity.
 */
public class UserDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String mobileNo;

    @NotNull
    private LocalDate dob;

    @NotNull
    private String houseNo;

    private Integer streetNo;

    @NotNull
    private City city;

    @NotNull
    private State state;

    private Integer pincode;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDetailsDTO userDetailsDTO = (UserDetailsDTO) o;
        if (userDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDetailsDTO{" +
            "id=" + getId() +
            ", mobileNo='" + getMobileNo() + "'" +
            ", dob='" + getDob() + "'" +
            ", houseNo='" + getHouseNo() + "'" +
            ", streetNo=" + getStreetNo() +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", pincode=" + getPincode() +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
