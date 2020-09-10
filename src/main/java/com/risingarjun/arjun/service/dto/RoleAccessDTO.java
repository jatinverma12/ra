package com.risingarjun.arjun.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.risingarjun.arjun.domain.RoleAccess} entity.
 */
public class RoleAccessDTO implements Serializable {

    private Long id;

    private Boolean create;

    private Boolean read;

    private Boolean update;

    private Boolean del;


    private Long roleId;

    private String roleName;

    private Long featureId;

    private String featureFeatureDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean isUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean isDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long jhiauthorityId) {
        this.roleId = jhiauthorityId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String jhiauthorityName) {
        this.roleName = jhiauthorityName;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featuresId) {
        this.featureId = featuresId;
    }

    public String getFeatureFeatureDetail() {
        return featureFeatureDetail;
    }

    public void setFeatureFeatureDetail(String featuresFeatureDetail) {
        this.featureFeatureDetail = featuresFeatureDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoleAccessDTO roleAccessDTO = (RoleAccessDTO) o;
        if (roleAccessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roleAccessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoleAccessDTO{" +
            "id=" + getId() +
            ", create='" + isCreate() + "'" +
            ", read='" + isRead() + "'" +
            ", update='" + isUpdate() + "'" +
            ", del='" + isDel() + "'" +
            ", role=" + getRoleId() +
            ", role='" + getRoleName() + "'" +
            ", feature=" + getFeatureId() +
            ", feature='" + getFeatureFeatureDetail() + "'" +
            "}";
    }
}
