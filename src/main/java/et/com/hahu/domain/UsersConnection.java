package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * The UsersConnection entity will store a user follower and following users.\n@author A true hailemaryam
 */
@Entity
@Table(name = "users_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UsersConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "registered_time")
    private Instant registeredTime;

    @ManyToOne
    @JsonIgnoreProperties("usersConnections")
    private User follower;

    @ManyToOne
    @JsonIgnoreProperties("usersConnections")
    private User following;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegisteredTime() {
        return registeredTime;
    }

    public UsersConnection registeredTime(Instant registeredTime) {
        this.registeredTime = registeredTime;
        return this;
    }

    public void setRegisteredTime(Instant registeredTime) {
        this.registeredTime = registeredTime;
    }

    public User getFollower() {
        return follower;
    }

    public UsersConnection follower(User user) {
        this.follower = user;
        return this;
    }

    public void setFollower(User user) {
        this.follower = user;
    }

    public User getFollowing() {
        return following;
    }

    public UsersConnection following(User user) {
        this.following = user;
        return this;
    }

    public void setFollowing(User user) {
        this.following = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsersConnection)) {
            return false;
        }
        return id != null && id.equals(((UsersConnection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UsersConnection{" +
            "id=" + getId() +
            ", registeredTime='" + getRegisteredTime() + "'" +
            "}";
    }
}
