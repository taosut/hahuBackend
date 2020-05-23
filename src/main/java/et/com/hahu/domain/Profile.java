package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Pattern(regexp = "^\\+[0-9]{12}$")
    @Column(name = "phone")
    private String phone;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "profile_family",
               joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "family_id", referencedColumnName = "id"))
    private Set<User> families = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public Profile phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getFamilies() {
        return families;
    }

    public Profile families(Set<User> users) {
        this.families = users;
        return this;
    }

    public Profile addFamily(User user) {
        this.families.add(user);
        return this;
    }

    public Profile removeFamily(User user) {
        this.families.remove(user);
        return this;
    }

    public void setFamilies(Set<User> users) {
        this.families = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
