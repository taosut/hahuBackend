package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import et.com.hahu.domain.enumeration.GroupType;
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
    /**
     * Class for filtering GroupType
     */
    public static class GroupTypeFilter extends Filter<GroupType> {

        public GroupTypeFilter() {
        }

        public GroupTypeFilter(GroupTypeFilter filter) {
            super(filter);
        }

        @Override
        public GroupTypeFilter copy() {
            return new GroupTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private GroupTypeFilter groupType;

    private LongFilter notificationId;

    private LongFilter scheduleId;

    private LongFilter userId;

    private LongFilter ownerId;

    private LongFilter schoolId;

    public UserGroupCriteria() {
    }

    public UserGroupCriteria(UserGroupCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.groupType = other.groupType == null ? null : other.groupType.copy();
        this.notificationId = other.notificationId == null ? null : other.notificationId.copy();
        this.scheduleId = other.scheduleId == null ? null : other.scheduleId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
        this.schoolId = other.schoolId == null ? null : other.schoolId.copy();
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public GroupTypeFilter getGroupType() {
        return groupType;
    }

    public void setGroupType(GroupTypeFilter groupType) {
        this.groupType = groupType;
    }

    public LongFilter getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(LongFilter notificationId) {
        this.notificationId = notificationId;
    }

    public LongFilter getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(LongFilter scheduleId) {
        this.scheduleId = scheduleId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public LongFilter getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(LongFilter schoolId) {
        this.schoolId = schoolId;
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
            Objects.equals(name, that.name) &&
            Objects.equals(groupType, that.groupType) &&
            Objects.equals(notificationId, that.notificationId) &&
            Objects.equals(scheduleId, that.scheduleId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(schoolId, that.schoolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        groupType,
        notificationId,
        scheduleId,
        userId,
        ownerId,
        schoolId
        );
    }

    @Override
    public String toString() {
        return "UserGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (groupType != null ? "groupType=" + groupType + ", " : "") +
                (notificationId != null ? "notificationId=" + notificationId + ", " : "") +
                (scheduleId != null ? "scheduleId=" + scheduleId + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
                (schoolId != null ? "schoolId=" + schoolId + ", " : "") +
            "}";
    }

}
