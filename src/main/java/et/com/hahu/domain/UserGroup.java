package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * The UserGroup entity will store user groups.\n@author A true hailemaryam
 */
@Entity
@Table(name = "user_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detail")
    private String detail;

    @Lob
    @Column(name = "profile_pic")
    private byte[] profilePic;

    @Column(name = "profile_pic_content_type")
    private String profilePicContentType;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_group_user",
               joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "user_group_owner",
               joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "id"))
    private Set<User> owners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public UserGroup detail(String detail) {
        this.detail = detail;
        return this;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public UserGroup profilePic(byte[] profilePic) {
        this.profilePic = profilePic;
        return this;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePicContentType() {
        return profilePicContentType;
    }

    public UserGroup profilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
        return this;
    }

    public void setProfilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
    }

    public Set<User> getUsers() {
        return users;
    }

    public UserGroup users(Set<User> users) {
        this.users = users;
        return this;
    }

    public UserGroup addUser(User user) {
        this.users.add(user);
        return this;
    }

    public UserGroup removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getOwners() {
        return owners;
    }

    public UserGroup owners(Set<User> users) {
        this.owners = users;
        return this;
    }

    public UserGroup addOwner(User user) {
        this.owners.add(user);
        return this;
    }

    public UserGroup removeOwner(User user) {
        this.owners.remove(user);
        return this;
    }

    public void setOwners(Set<User> users) {
        this.owners = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserGroup)) {
            return false;
        }
        return id != null && id.equals(((UserGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", profilePic='" + getProfilePic() + "'" +
            ", profilePicContentType='" + getProfilePicContentType() + "'" +
            "}";
    }
}
