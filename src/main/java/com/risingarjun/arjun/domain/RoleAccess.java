package com.risingarjun.arjun.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RoleAccess.
 */
@Entity
@Table(name = "role_access")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoleAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_create")
    private Boolean create;

    @Column(name = "jhi_read")
    private Boolean read;

    @Column(name = "jhi_update")
    private Boolean update;

    @Column(name = "del")
    private Boolean del;

    @ManyToOne
    @JsonIgnoreProperties("roleAccesses")
    private Jhiauthority role;

    @ManyToOne
    @JsonIgnoreProperties("roleAccesses")
    private Features feature;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCreate() {
        return create;
    }

    public RoleAccess create(Boolean create) {
        this.create = create;
        return this;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean isRead() {
        return read;
    }

    public RoleAccess read(Boolean read) {
        this.read = read;
        return this;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean isUpdate() {
        return update;
    }

    public RoleAccess update(Boolean update) {
        this.update = update;
        return this;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean isDel() {
        return del;
    }

    public RoleAccess del(Boolean del) {
        this.del = del;
        return this;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Jhiauthority getRole() {
        return role;
    }

    public RoleAccess role(Jhiauthority jhiauthority) {
        this.role = jhiauthority;
        return this;
    }

    public void setRole(Jhiauthority jhiauthority) {
        this.role = jhiauthority;
    }

    public Features getFeature() {
        return feature;
    }

    public RoleAccess feature(Features features) {
        this.feature = features;
        return this;
    }

    public void setFeature(Features features) {
        this.feature = features;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleAccess)) {
            return false;
        }
        return id != null && id.equals(((RoleAccess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RoleAccess{" +
            "id=" + getId() +
            ", create='" + isCreate() + "'" +
            ", read='" + isRead() + "'" +
            ", update='" + isUpdate() + "'" +
            ", del='" + isDel() + "'" +
            "}";
    }
}
