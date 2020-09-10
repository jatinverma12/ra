package com.risingarjun.arjun.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.Features} entity.
 */
public class FeaturesDTO implements Serializable {

    private Long id;

    @NotNull
    private String featureId;

    @NotNull
    private String featureDetail;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeatureId() {
        return featureId;
    }

    public void setFeatureId(String featureId) {
        this.featureId = featureId;
    }

    public String getFeatureDetail() {
        return featureDetail;
    }

    public void setFeatureDetail(String featureDetail) {
        this.featureDetail = featureDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeaturesDTO featuresDTO = (FeaturesDTO) o;
        if (featuresDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), featuresDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeaturesDTO{" +
            "id=" + getId() +
            ", featureId='" + getFeatureId() + "'" +
            ", featureDetail='" + getFeatureDetail() + "'" +
            "}";
    }
}
