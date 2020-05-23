package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link et.com.hahu.domain.UsersConnection} entity.
 */
@ApiModel(description = "The UsersConnection entity will store a user follower and following users.\n@author A true hailemaryam")
public class UsersConnectionDTO implements Serializable {
    
    private Long id;

    private Instant registeredTime;


    private Long followerId;

    private String followerLogin;

    private Long followingId;

    private String followingLogin;
    
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

    public Long getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Long userId) {
        this.followerId = userId;
    }

    public String getFollowerLogin() {
        return followerLogin;
    }

    public void setFollowerLogin(String userLogin) {
        this.followerLogin = userLogin;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long userId) {
        this.followingId = userId;
    }

    public String getFollowingLogin() {
        return followingLogin;
    }

    public void setFollowingLogin(String userLogin) {
        this.followingLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsersConnectionDTO)) {
            return false;
        }

        return id != null && id.equals(((UsersConnectionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersConnectionDTO{" +
            "id=" + getId() +
            ", registeredTime='" + getRegisteredTime() + "'" +
            ", followerId=" + getFollowerId() +
            ", followerLogin='" + getFollowerLogin() + "'" +
            ", followingId=" + getFollowingId() +
            ", followingLogin='" + getFollowingLogin() + "'" +
            "}";
    }
}
