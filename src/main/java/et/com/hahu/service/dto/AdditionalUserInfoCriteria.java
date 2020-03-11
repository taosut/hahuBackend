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

/**
 * Criteria class for the {@link et.com.hahu.domain.AdditionalUserInfo} entity. This class is used
 * in {@link et.com.hahu.web.rest.AdditionalUserInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /additional-user-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AdditionalUserInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phone;

    private StringFilter profilePicture;

    private LongFilter userId;

    private LongFilter followingId;

    private LongFilter followerId;

    private LongFilter userGroupId;

    public AdditionalUserInfoCriteria() {
    }

    public AdditionalUserInfoCriteria(AdditionalUserInfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.profilePicture = other.profilePicture == null ? null : other.profilePicture.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.followingId = other.followingId == null ? null : other.followingId.copy();
        this.followerId = other.followerId == null ? null : other.followerId.copy();
        this.userGroupId = other.userGroupId == null ? null : other.userGroupId.copy();
    }

    @Override
    public AdditionalUserInfoCriteria copy() {
        return new AdditionalUserInfoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(StringFilter profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }

    public LongFilter getFollowerId() {
        return followerId;
    }

    public void setFollowerId(LongFilter followerId) {
        this.followerId = followerId;
    }

    public LongFilter getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(LongFilter userGroupId) {
        this.userGroupId = userGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AdditionalUserInfoCriteria that = (AdditionalUserInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(profilePicture, that.profilePicture) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(followingId, that.followingId) &&
            Objects.equals(followerId, that.followerId) &&
            Objects.equals(userGroupId, that.userGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phone,
        profilePicture,
        userId,
        followingId,
        followerId,
        userGroupId
        );
    }

    @Override
    public String toString() {
        return "AdditionalUserInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (profilePicture != null ? "profilePicture=" + profilePicture + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (followingId != null ? "followingId=" + followingId + ", " : "") +
                (followerId != null ? "followerId=" + followerId + ", " : "") +
                (userGroupId != null ? "userGroupId=" + userGroupId + ", " : "") +
            "}";
    }

}
