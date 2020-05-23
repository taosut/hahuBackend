package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import et.com.hahu.domain.enumeration.ContentType;
import et.com.hahu.domain.enumeration.PostType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link et.com.hahu.domain.Post} entity. This class is used
 * in {@link et.com.hahu.web.rest.PostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ContentType
     */
    public static class ContentTypeFilter extends Filter<ContentType> {

        public ContentTypeFilter() {
        }

        public ContentTypeFilter(ContentTypeFilter filter) {
            super(filter);
        }

        @Override
        public ContentTypeFilter copy() {
            return new ContentTypeFilter(this);
        }

    }
    /**
     * Class for filtering PostType
     */
    public static class PostTypeFilter extends Filter<PostType> {

        public PostTypeFilter() {
        }

        public PostTypeFilter(PostTypeFilter filter) {
            super(filter);
        }

        @Override
        public PostTypeFilter copy() {
            return new PostTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private ContentTypeFilter contentType;

    private PostTypeFilter postType;

    private InstantFilter postedDate;

    private InstantFilter modifiedDate;

    private InstantFilter instantPostEndDate;

    private LongFilter postMetaDataId;

    private LongFilter commentId;

    private LongFilter likeId;

    private LongFilter userId;

    private LongFilter categoryId;

    private LongFilter tagId;

    public PostCriteria() {
    }

    public PostCriteria(PostCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.contentType = other.contentType == null ? null : other.contentType.copy();
        this.postType = other.postType == null ? null : other.postType.copy();
        this.postedDate = other.postedDate == null ? null : other.postedDate.copy();
        this.modifiedDate = other.modifiedDate == null ? null : other.modifiedDate.copy();
        this.instantPostEndDate = other.instantPostEndDate == null ? null : other.instantPostEndDate.copy();
        this.postMetaDataId = other.postMetaDataId == null ? null : other.postMetaDataId.copy();
        this.commentId = other.commentId == null ? null : other.commentId.copy();
        this.likeId = other.likeId == null ? null : other.likeId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.tagId = other.tagId == null ? null : other.tagId.copy();
    }

    @Override
    public PostCriteria copy() {
        return new PostCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public ContentTypeFilter getContentType() {
        return contentType;
    }

    public void setContentType(ContentTypeFilter contentType) {
        this.contentType = contentType;
    }

    public PostTypeFilter getPostType() {
        return postType;
    }

    public void setPostType(PostTypeFilter postType) {
        this.postType = postType;
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

    public InstantFilter getInstantPostEndDate() {
        return instantPostEndDate;
    }

    public void setInstantPostEndDate(InstantFilter instantPostEndDate) {
        this.instantPostEndDate = instantPostEndDate;
    }

    public LongFilter getPostMetaDataId() {
        return postMetaDataId;
    }

    public void setPostMetaDataId(LongFilter postMetaDataId) {
        this.postMetaDataId = postMetaDataId;
    }

    public LongFilter getCommentId() {
        return commentId;
    }

    public void setCommentId(LongFilter commentId) {
        this.commentId = commentId;
    }

    public LongFilter getLikeId() {
        return likeId;
    }

    public void setLikeId(LongFilter likeId) {
        this.likeId = likeId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getTagId() {
        return tagId;
    }

    public void setTagId(LongFilter tagId) {
        this.tagId = tagId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostCriteria that = (PostCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(contentType, that.contentType) &&
            Objects.equals(postType, that.postType) &&
            Objects.equals(postedDate, that.postedDate) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(instantPostEndDate, that.instantPostEndDate) &&
            Objects.equals(postMetaDataId, that.postMetaDataId) &&
            Objects.equals(commentId, that.commentId) &&
            Objects.equals(likeId, that.likeId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        contentType,
        postType,
        postedDate,
        modifiedDate,
        instantPostEndDate,
        postMetaDataId,
        commentId,
        likeId,
        userId,
        categoryId,
        tagId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (contentType != null ? "contentType=" + contentType + ", " : "") +
                (postType != null ? "postType=" + postType + ", " : "") +
                (postedDate != null ? "postedDate=" + postedDate + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
                (instantPostEndDate != null ? "instantPostEndDate=" + instantPostEndDate + ", " : "") +
                (postMetaDataId != null ? "postMetaDataId=" + postMetaDataId + ", " : "") +
                (commentId != null ? "commentId=" + commentId + ", " : "") +
                (likeId != null ? "likeId=" + likeId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (tagId != null ? "tagId=" + tagId + ", " : "") +
            "}";
    }

}
