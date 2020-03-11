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
 * Criteria class for the {@link et.com.hahu.domain.UserGroup} entity. This class is used
 * in {@link et.com.hahu.web.rest.UserGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupName;

    private LongFilter additionalUserInfoId;

    public UserGroupCriteria() {
    }

    public UserGroupCriteria(UserGroupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.groupName = other.groupName == null ? null : other.groupName.copy();
        this.additionalUserInfoId = other.additionalUserInfoId == null ? null : other.additionalUserInfoId.copy();
    }

    @Override
    public UserGroupCriteria copy() {
        return new UserGroupCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getGroupName() {
        return groupName;
    }

    public void setGroupName(StringFilter groupName) {
        this.groupName = groupName;
    }

    public LongFilter getAdditionalUserInfoId() {
        return additionalUserInfoId;
    }

    public void setAdditionalUserInfoId(LongFilter additionalUserInfoId) {
        this.additionalUserInfoId = additionalUserInfoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserGroupCriteria that = (UserGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(groupName, that.groupName) &&
            Objects.equals(additionalUserInfoId, that.additionalUserInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        groupName,
        additionalUserInfoId
        );
    }

    @Override
    public String toString() {
        return "UserGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (groupName != null ? "groupName=" + groupName + ", " : "") +
                (additionalUserInfoId != null ? "additionalUserInfoId=" + additionalUserInfoId + ", " : "") +
            "}";
    }

}
