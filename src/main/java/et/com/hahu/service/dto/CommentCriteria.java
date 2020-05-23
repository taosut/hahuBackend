package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link et.com.hahu.domain.Comment} entity. This class is used
 * in {@link et.com.hahu.web.rest.CommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter postedDate;

    private InstantFilter modifiedDate;

    private LongFilter replyId;

    private LongFilter likeId;

    private LongFilter postId;

    private LongFilter commentId;

    public CommentCriteria() {
    }

    public CommentCriteria(CommentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.postedDate = other.postedDate == null ? null : other.postedDate.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.replyId = other.replyId == null ? null : other.replyId.copy();
        this.likeId = other.likeId == null ? null : other.likeId.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
        this.commentId = other.commentId == null ? null : other.commentId.copy();
    }

    @Override
    public CommentCriteria copy() {
        return new CommentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(InstantFilter postedDate) {
        this.postedDate = postedDate;
    }

    public InstantFilter getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(InstantFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LongFilter getReplyId() {
        return replyId;
    }

    public void setReplyId(LongFilter replyId) {
        this.replyId = replyId;
    }

    public LongFilter getLikeId() {
        return likeId;
    }

    public void setLikeId(LongFilter likeId) {
        this.likeId = likeId;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }

    public LongFilter getCommentId() {
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
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
        final CommentCriteria that = (CommentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(postedDate, that.postedDate) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(replyId, that.replyId) &&
            Objects.equals(likeId, that.likeId) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        postedDate,
        modifiedDate,
        replyId,
        likeId,
        postId,
        commentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (postedDate != null ? "postedDate=" + postedDate + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
                (replyId != null ? "replyId=" + replyId + ", " : "") +
                (likeId != null ? "likeId=" + likeId + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
                (commentId != null ? "commentId=" + commentId + ", " : "") +
            "}";
    }

}
