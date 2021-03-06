package et.com.hahu.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link et.com.hahu.domain.Profile} entity.
 */
public class ProfileDTO implements Serializable {
    
    private Long id;

    private Integer age;

    @Pattern(regexp = "^\\+[0-9]{12}$")
    private String phone;

    @Lob
    private byte[] curentProfilePic;

    private String curentProfilePicContentType;

    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getCurentProfilePic() {
        return curentProfilePic;
    }

    public void setCurentProfilePic(byte[] curentProfilePic) {
        this.curentProfilePic = curentProfilePic;
    }

    public String getCurentProfilePicContentType() {
        return curentProfilePicContentType;
    }

    public void setCurentProfilePicContentType(String curentProfilePicContentType) {
        this.curentProfilePicContentType = curentProfilePicContentType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProfileDTO)) {
            return false;
        }

        return id != null && id.equals(((ProfileDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProfileDTO{" +
            "id=" + getId() +
            ", age=" + getAge() +
            ", phone='" + getPhone() + "'" +
            ", curentProfilePic='" + getCurentProfilePic() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
