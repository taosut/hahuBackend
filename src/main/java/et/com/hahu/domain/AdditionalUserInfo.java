package et.com.hahu.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A AdditionalUserInfo.
 */
@Entity
@Table(name = "additional_user_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdditionalUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Pattern(regexp = "^\\+[0-9]{12}$")
    @Column(name = "phone")
    private String phone;

    @Column(name = "profile_picture")
    private String profilePicture;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "follower")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UsersConnection> followings = new HashSet<>();

    @OneToMany(mappedBy = "following")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<UsersConnection> followers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("additionalUserInfos")
    private UserGroup userGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public AdditionalUserInfo phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public AdditionalUserInfo profilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public User getUser() {
        return user;
    }

    public AdditionalUserInfo user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<UsersConnection> getFollowings() {
        return followings;
    }

    public AdditionalUserInfo followings(Set<UsersConnection> usersConnections) {
        this.followings = usersConnections;
        return this;
    }

    public AdditionalUserInfo addFollowing(UsersConnection usersConnection) {
        this.followings.add(usersConnection);
        usersConnection.setFollower(this);
        return this;
    }

    public AdditionalUserInfo removeFollowing(UsersConnection usersConnection) {
        this.followings.remove(usersConnection);
        usersConnection.setFollower(null);
        return this;
    }

    public void setFollowings(Set<UsersConnection> usersConnections) {
        this.followings = usersConnections;
    }

    public Set<UsersConnection> getFollowers() {
        return followers;
    }

    public AdditionalUserInfo followers(Set<UsersConnection> usersConnections) {
        this.followers = usersConnections;
        return this;
    }

    public AdditionalUserInfo addFollower(UsersConnection usersConnection) {
        this.followers.add(usersConnection);
        usersConnection.setFollowing(this);
        return this;
    }

    public AdditionalUserInfo removeFollower(UsersConnection usersConnection) {
        this.followers.remove(usersConnection);
        usersConnection.setFollowing(null);
        return this;
    }

    public void setFollowers(Set<UsersConnection> usersConnections) {
        this.followers = usersConnections;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public AdditionalUserInfo userGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
        return this;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdditionalUserInfo)) {
            return false;
        }
        return id != null && id.equals(((AdditionalUserInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AdditionalUserInfo{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            "}";
    }
}
