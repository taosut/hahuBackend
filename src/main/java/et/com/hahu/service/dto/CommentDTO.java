package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link et.com.hahu.domain.Comment} entity.
 */
@ApiModel(description = "The Comment entity will contain comments on post, user or comment.\n@author A true hailemaryam")
public class CommentDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private String content;

    private Instant postedDate;

    private Instant modifiedDate;


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Instant postedDate) {
        this.postedDate = postedDate;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
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
        if (!(o instanceof CommentDTO)) {
            return false;
        }

        return id != null && id.equals(((CommentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", postId=" + getPostId() +
            ", commentId=" + getCommentId() +
            "}";
    }
}
