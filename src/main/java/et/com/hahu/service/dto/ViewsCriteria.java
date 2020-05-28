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
 * Criteria class for the {@link et.com.hahu.domain.Views} entity. This class is used
 * in {@link et.com.hahu.web.rest.ViewsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /views?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ViewsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter registeredTime;

    private LongFilter userId;

    private LongFilter postId;

    public ViewsCriteria() {
    }

    public ViewsCriteria(ViewsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.registeredTime = other.registeredTime == null ? null : other.registeredTime.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
    }

    @Override
    public ViewsCriteria copy() {
        return new ViewsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getRegisteredTime() {
        return registeredTime;
    }

    public void setRegisteredTime(InstantFilter registeredTime) {
        this.registeredTime = registeredTime;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getPostId() {
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ViewsCriteria that = (ViewsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registeredTime, that.registeredTime) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(postId, that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registeredTime,
        userId,
        postId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registeredTime != null ? "registeredTime=" + registeredTime + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (postId != null ? "postId=" + postId + ", " : "") +
            "}";
    }

}
