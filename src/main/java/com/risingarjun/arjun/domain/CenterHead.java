package com.risingarjun.arjun.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CenterHead.
 */
@Entity
@Table(name = "center_head")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CenterHead implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Employees centerhead;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "center_head_center",
               joinColumns = @JoinColumn(name = "center_head_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "center_id", referencedColumnName = "id"))
    private Set<Centers> centers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employees getCenterhead() {
        return centerhead;
    }

    public CenterHead centerhead(Employees employees) {
        this.centerhead = employees;
        return this;
    }

    public void setCenterhead(Employees employees) {
        this.centerhead = employees;
    }

    public Set<Centers> getCenters() {
        return centers;
    }

    public CenterHead centers(Set<Centers> centers) {
        this.centers = centers;
        return this;
    }

    public CenterHead addCenter(Centers centers) {
        this.centers.add(centers);
        centers.getCenterheads().add(this);
        return this;
    }

    public CenterHead removeCenter(Centers centers) {
        this.centers.remove(centers);
        centers.getCenterheads().remove(this);
        return this;
    }

    public void setCenters(Set<Centers> centers) {
        this.centers = centers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CenterHead)) {
            return false;
        }
        return id != null && id.equals(((CenterHead) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CenterHead{" +
            "id=" + getId() +
            "}";
    }
}
