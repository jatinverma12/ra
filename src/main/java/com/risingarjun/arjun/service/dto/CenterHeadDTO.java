package com.risingarjun.arjun.service.dto;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.CenterHead} entity.
 */
public class CenterHeadDTO implements Serializable {

    private Long id;


    private Long centerheadId;

    private String centerheadEmployeeId;

    private Set<CentersDTO> centers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCenterheadId() {
        return centerheadId;
    }

    public void setCenterheadId(Long employeesId) {
        this.centerheadId = employeesId;
    }

    public String getCenterheadEmployeeId() {
        return centerheadEmployeeId;
    }

    public void setCenterheadEmployeeId(String employeesEmployeeId) {
        this.centerheadEmployeeId = employeesEmployeeId;
    }

    public Set<CentersDTO> getCenters() {
        return centers;
    }

    public void setCenters(Set<CentersDTO> centers) {
        this.centers = centers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CenterHeadDTO centerHeadDTO = (CenterHeadDTO) o;
        if (centerHeadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centerHeadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CenterHeadDTO{" +
            "id=" + getId() +
            ", centerhead=" + getCenterheadId() +
            ", centerhead='" + getCenterheadEmployeeId() + "'" +
            "}";
    }
}
