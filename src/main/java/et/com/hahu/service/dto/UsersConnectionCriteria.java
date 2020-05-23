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
 * Criteria class for the {@link et.com.hahu.domain.UsersConnection} entity. This class is used
 * in {@link et.com.hahu.web.rest.UsersConnectionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /users-connections?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UsersConnectionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter registeredTime;

    private LongFilter followerId;

    private LongFilter followingId;

    public UsersConnectionCriteria() {
    }

    public UsersConnectionCriteria(UsersConnectionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.registeredTime = other.registeredTime == null ? null : other.registeredTime.copy();
        this.followerId = other.followerId == null ? null : other.followerId.copy();
        this.followingId = other.followingId == null ? null : other.followingId.copy();
    }

    @Override
    public UsersConnectionCriteria copy() {
        return new UsersConnectionCriteria(this);
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

    public LongFilter getFollowerId() {
        return followerId;
    }

    public void setFollowerId(LongFilter followerId) {
        this.followerId = followerId;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UsersConnectionCriteria that = (UsersConnectionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registeredTime, that.registeredTime) &&
            Objects.equals(followerId, that.followerId) &&
            Objects.equals(followingId, that.followingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registeredTime,
        followerId,
        followingId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersConnectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registeredTime != null ? "registeredTime=" + registeredTime + ", " : "") +
                (followerId != null ? "followerId=" + followerId + ", " : "") +
                (followingId != null ? "followingId=" + followingId + ", " : "") +
            "}";
    }

}
