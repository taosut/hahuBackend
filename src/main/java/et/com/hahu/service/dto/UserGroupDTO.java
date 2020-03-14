package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link et.com.hahu.domain.UserGroup} entity.
 */
@ApiModel(description = "The UserGroup entity will store user groups.\n@author A true hailemaryam")
public class UserGroupDTO implements Serializable {
    
    private Long id;

    private String name;

    @Lob
    private String detail;

    @Lob
    private byte[] profilePic;

    private String profilePicContentType;
    private Set<UserDTO> users = new HashSet<>();
    private Set<UserDTO> owners = new HashSet<>();
    
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getProfilePicContentType() {
        return profilePicContentType;
    }

    public void setProfilePicContentType(String profilePicContentType) {
        this.profilePicContentType = profilePicContentType;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public Set<UserDTO> getOwners() {
        return owners;
    }

    public void setOwners(Set<UserDTO> users) {
        this.owners = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserGroupDTO userGroupDTO = (UserGroupDTO) o;
        if (userGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", detail='" + getDetail() + "'" +
            ", profilePic='" + getProfilePic() + "'" +
            ", users='" + getUsers() + "'" +
            ", owners='" + getOwners() + "'" +
            "}";
    }
}
