package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Category entity contains category for posts\n@author A true hailemaryam
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "recomendation_category")
    private Boolean recomendationCategory;

    @ManyToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Post> posts = new HashSet<>();

    @ManyToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Preference> preferences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isRecomendationCategory() {
        return recomendationCategory;
    }

    public Category recomendationCategory(Boolean recomendationCategory) {
        this.recomendationCategory = recomendationCategory;
        return this;
    }

    public void setRecomendationCategory(Boolean recomendationCategory) {
        this.recomendationCategory = recomendationCategory;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Category posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Category addPost(Post post) {
        this.posts.add(post);
        post.getCategories().add(this);
        return this;
    }

    public Category removePost(Post post) {
        this.posts.remove(post);
        post.getCategories().remove(this);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<Preference> getPreferences() {
        return preferences;
    }

    public Category preferences(Set<Preference> preferences) {
        this.preferences = preferences;
        return this;
    }

    public Category addPreference(Preference preference) {
        this.preferences.add(preference);
        preference.getCategories().add(this);
        return this;
    }

    public Category removePreference(Preference preference) {
        this.preferences.remove(preference);
        preference.getCategories().remove(this);
        return this;
    }

    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", recomendationCategory='" + isRecomendationCategory() + "'" +
            "}";
    }
}
