package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Preference.
 */
@Entity
@Table(name = "preference")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "has_preferece_selected")
    private Boolean hasPrefereceSelected;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "preference_category",
               joinColumns = @JoinColumn(name = "preference_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasPrefereceSelected() {
        return hasPrefereceSelected;
    }

    public Preference hasPrefereceSelected(Boolean hasPrefereceSelected) {
        this.hasPrefereceSelected = hasPrefereceSelected;
        return this;
    }

    public void setHasPrefereceSelected(Boolean hasPrefereceSelected) {
        this.hasPrefereceSelected = hasPrefereceSelected;
    }

    public User getUser() {
        return user;
    }

    public Preference user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Preference categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Preference addCategory(Category category) {
        this.categories.add(category);
        category.getPreferences().add(this);
        return this;
    }

    public Preference removeCategory(Category category) {
        this.categories.remove(category);
        category.getPreferences().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preference)) {
            return false;
        }
        return id != null && id.equals(((Preference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Preference{" +
            "id=" + getId() +
            ", hasPrefereceSelected='" + isHasPrefereceSelected() + "'" +
            "}";
    }
}
