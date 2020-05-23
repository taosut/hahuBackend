package et.com.hahu.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import et.com.hahu.domain.enumeration.ContentType;

/**
 * A DTO for the {@link et.com.hahu.domain.School} entity.
 */
public class SchoolDTO implements Serializable {
    
    private Long id;

    private String name;

    @Lob
    private byte[] featuredImage;

    private String featuredImageContentType;
    @Pattern(regexp = "^\\+[0-9]{12}$")
    private String phone;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    private String website;

    @Lob
    private String about;

    private ContentType aboutType;

    @Lob
    private String location;

    private ContentType locationType;

    private Set<UserDTO> users = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(byte[] featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getFeaturedImageContentType() {
        return featuredImageContentType;
    }

    public void setFeaturedImageContentType(String featuredImageContentType) {
        this.featuredImageContentType = featuredImageContentType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public ContentType getAboutType() {
        return aboutType;
    }

    public void setAboutType(ContentType aboutType) {
        this.aboutType = aboutType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ContentType getLocationType() {
        return locationType;
    }

    public void setLocationType(ContentType locationType) {
        this.locationType = locationType;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchoolDTO schoolDTO = (SchoolDTO) o;
        if (schoolDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SchoolDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", featuredImage='" + getFeaturedImage() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", about='" + getAbout() + "'" +
            ", aboutType='" + getAboutType() + "'" +
            ", location='" + getLocation() + "'" +
            ", locationType='" + getLocationType() + "'" +
            ", users='" + getUsers() + "'" +
            "}";
    }
}
