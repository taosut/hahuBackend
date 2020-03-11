package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link et.com.hahu.domain.UserGroup} entity.
 */
@ApiModel(description = "The UserGroup entity will store user groups.\n@author A true hailemaryam")
public class UserGroupDTO implements Serializable {
    
    private Long id;

    private String groupName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
            ", groupName='" + getGroupName() + "'" +
            "}";
    }
}
