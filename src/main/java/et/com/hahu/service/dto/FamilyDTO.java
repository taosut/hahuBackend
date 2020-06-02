package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link et.com.hahu.domain.Family} entity.
 */
public class FamilyDTO implements Serializable {
    
    private Long id;

    private Boolean hasFamilyControl;


    private Long userId;

    private String userLogin;
    private Set<UserDTO> parents = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isHasFamilyControl() {
        return hasFamilyControl;
    }

    public void setHasFamilyControl(Boolean hasFamilyControl) {
        this.hasFamilyControl = hasFamilyControl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Set<UserDTO> getParents() {
        return parents;
    }

    public void setParents(Set<UserDTO> users) {
        this.parents = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyDTO)) {
            return false;
        }

        return id != null && id.equals(((FamilyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyDTO{" +
            "id=" + getId() +
            ", hasFamilyControl='" + isHasFamilyControl() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", parents='" + getParents() + "'" +
            "}";
    }
}
