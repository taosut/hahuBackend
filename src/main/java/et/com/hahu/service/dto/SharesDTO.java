package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link et.com.hahu.domain.Shares} entity.
 */
@ApiModel(description = "The Shares entity contain shares for posts.\n@author A true hailemaryam")
public class SharesDTO implements Serializable {
    
    private Long id;

    private Instant registeredTime;


    private Long userId;

    private String userLogin;

    private Long postId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(Instant registeredTime) {
        this.registeredTime = registeredTime;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SharesDTO)) {
            return false;
        }

        return id != null && id.equals(((SharesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SharesDTO{" +
            "id=" + getId() +
            ", registeredTime='" + getRegisteredTime() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", postId=" + getPostId() +
            "}";
    }
}
