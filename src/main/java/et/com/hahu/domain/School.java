package et.com.hahu.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import et.com.hahu.domain.enumeration.ContentType;

/**
 * A School.
 */
@Entity
@Table(name = "school")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class School implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "featured_image")
    private byte[] featuredImage;

    @Column(name = "featured_image_content_type")
    private String featuredImageContentType;

    @Pattern(regexp = "^\\+[0-9]{12}$")
    @Column(name = "phone")
    private String phone;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "about")
    private String about;

    @Enumerated(EnumType.STRING)
    @Column(name = "about_type")
    private ContentType aboutType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type")
    private ContentType locationType;

    @OneToMany(mappedBy = "school")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserGroup> userGroups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "school_user",
               joinColumns = @JoinColumn(name = "school_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

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

    public School name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFeaturedImage() {
        return featuredImage;
    }

    public School featuredImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
        return this;
    }

    public void setFeaturedImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageContentType() {
        return featuredImageContentType;
    }

    public School featuredImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
        return this;
    }

    public void setFeaturedImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
    }

    public String getPhone() {
        return phone;
    }

    public School phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public School email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public School website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAbout() {
        return about;
    }

    public School about(String about) {
        this.about = about;
        return this;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ContentType getAboutType() {
        return aboutType;
    }

    public School aboutType(ContentType aboutType) {
        this.aboutType = aboutType;
        return this;
    }

    public void setAboutType(ContentType aboutType) {
        this.aboutType = aboutType;
    }

    public String getLocation() {
        return location;
    }

    public School location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ContentType getLocationType() {
        return locationType;
    }

    public School locationType(ContentType locationType) {
        this.locationType = locationType;
        return this;
    }

    public void setLocationType(ContentType locationType) {
        this.locationType = locationType;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public School userGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
        return this;
    }

    public School addUserGroup(UserGroup userGroup) {
        this.userGroups.add(userGroup);
        userGroup.setSchool(this);
        return this;
    }

    public School removeUserGroup(UserGroup userGroup) {
        this.userGroups.remove(userGroup);
        userGroup.setSchool(null);
        return this;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public Set<User> getUsers() {
        return users;
    }

    public School users(Set<User> users) {
        this.users = users;
        return this;
    }

    public School addUser(User user) {
        this.users.add(user);
        return this;
    }

    public School removeUser(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof School)) {
            return false;
        }
        return id != null && id.equals(((School) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "School{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", featuredImage='" + getFeaturedImage() + "'" +
            ", featuredImageContentType='" + getFeaturedImageContentType() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", about='" + getAbout() + "'" +
            ", aboutType='" + getAboutType() + "'" +
            ", location='" + getLocation() + "'" +
            ", locationType='" + getLocationType() + "'" +
            "}";
    }
}
