package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * The Views entity contain views for posts.\n@author A true hailemaryam
 */
@Entity
@Table(name = "views")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Views implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "registered_time")
    private Instant registeredTime;

    @ManyToOne
    @JsonIgnoreProperties(value = "views", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "views", allowSetters = true)
    private Post post;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegisteredTime() {
        return registeredTime;
    }

    public Views registeredTime(Instant registeredTime) {
        this.registeredTime = registeredTime;
        return this;
    }

    public void setRegisteredTime(Instant registeredTime) {
        this.registeredTime = registeredTime;
    }

    public User getUser() {
        return user;
    }

    public Views user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public Views post(Post post) {
        this.post = post;
        return this;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Views)) {
            return false;
        }
        return id != null && id.equals(((Views) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Views{" +
            "id=" + getId() +
            ", registeredTime='" + getRegisteredTime() + "'" +
            "}";
    }
}
