package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Family.
 */
@Entity
@Table(name = "family")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Family implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "has_family_control")
    private Boolean hasFamilyControl;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "family_parent",
               joinColumns = @JoinColumn(name = "family_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "parent_id", referencedColumnName = "id"))
    private Set<User> parents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasFamilyControl() {
        return hasFamilyControl;
    }

    public Family hasFamilyControl(Boolean hasFamilyControl) {
        this.hasFamilyControl = hasFamilyControl;
        return this;
    }

    public void setHasFamilyControl(Boolean hasFamilyControl) {
        this.hasFamilyControl = hasFamilyControl;
    }

    public User getUser() {
        return user;
    }

    public Family user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getParents() {
        return parents;
    }

    public Family parents(Set<User> users) {
        this.parents = users;
        return this;
    }

    public Family addParent(User user) {
        this.parents.add(user);
        return this;
    }

    public Family removeParent(User user) {
        this.parents.remove(user);
        return this;
    }

    public void setParents(Set<User> users) {
        this.parents = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Family)) {
            return false;
        }
        return id != null && id.equals(((Family) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Family{" +
            "id=" + getId() +
            ", hasFamilyControl='" + isHasFamilyControl() + "'" +
            "}";
    }
}
