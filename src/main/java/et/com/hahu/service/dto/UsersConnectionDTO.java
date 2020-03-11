package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link et.com.hahu.domain.UsersConnection} entity.
 */
@ApiModel(description = "The UsersConnection entity will store a user follower and following users.\n@author A true hailemaryam")
public class UsersConnectionDTO implements Serializable {
    
    private Long id;

    private Instant registeredTime;


    private Long followerId;

    private Long followingId;
    
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

    public void setFollowerId(Long additionalUserInfoId) {
        this.followerId = additionalUserInfoId;
    }

    public Long getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Long additionalUserInfoId) {
        this.followingId = additionalUserInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UsersConnectionDTO usersConnectionDTO = (UsersConnectionDTO) o;
        if (usersConnectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), usersConnectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UsersConnectionDTO{" +
            "id=" + getId() +
            ", registeredTime='" + getRegisteredTime() + "'" +
            ", followerId=" + getFollowerId() +
            ", followingId=" + getFollowingId() +
            "}";
    }
}
