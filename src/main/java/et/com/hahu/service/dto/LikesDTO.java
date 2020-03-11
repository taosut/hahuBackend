package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link et.com.hahu.domain.Likes} entity.
 */
@ApiModel(description = "The Likes entity contain likes for user, comment, posts.\n@author A true hailemaryam")
public class LikesDTO implements Serializable {
    
    private Long id;

    private Instant registeredTime;


    private Long userId;

    private String userLogin;

    private Long postId;

    private Long commentId;
    
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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LikesDTO likesDTO = (LikesDTO) o;
        if (likesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), likesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LikesDTO{" +
            "id=" + getId() +
            ", registeredTime='" + getRegisteredTime() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", postId=" + getPostId() +
            ", commentId=" + getCommentId() +
            "}";
    }
}
