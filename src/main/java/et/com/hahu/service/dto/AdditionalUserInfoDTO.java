package et.com.hahu.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link et.com.hahu.domain.AdditionalUserInfo} entity.
 */
public class AdditionalUserInfoDTO implements Serializable {
    
    private Long id;

    @Pattern(regexp = "^\\+[0-9]{12}$")
    private String phone;

    private String profilePicture;


    private Long userId;

    private String userLogin;

    private Long userGroupId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdditionalUserInfoDTO additionalUserInfoDTO = (AdditionalUserInfoDTO) o;
        if (additionalUserInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), additionalUserInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdditionalUserInfoDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", userGroupId=" + getUserGroupId() +
            "}";
    }
}
