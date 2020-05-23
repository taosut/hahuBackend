package et.com.hahu.service.dto;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
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

        CommentDTO commentDTO = (CommentDTO) o;
        if (commentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", postedDate='" + getPostedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", postId=" + getPostId() +
            ", commentId=" + getCommentId() +
            "}";
    }
}
