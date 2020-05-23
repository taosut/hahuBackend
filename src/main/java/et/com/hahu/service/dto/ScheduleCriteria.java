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
 * Criteria class for the {@link et.com.hahu.domain.Schedule} entity. This class is used
 * in {@link et.com.hahu.web.rest.ScheduleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /schedules?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ScheduleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private InstantFilter startTime;

    private InstantFilter endTime;

    private LongFilter userId;

    private LongFilter userGroupId;

    private LongFilter scheduleTypeId;

    public ScheduleCriteria() {
    }

    public ScheduleCriteria(ScheduleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
        this.endTime = other.endTime == null ? null : other.endTime.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userGroupId = other.userGroupId == null ? null : other.userGroupId.copy();
        this.scheduleTypeId = other.scheduleTypeId == null ? null : other.scheduleTypeId.copy();
    }

    @Override
    public ScheduleCriteria copy() {
        return new ScheduleCriteria(this);
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

    public InstantFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(InstantFilter startTime) {
        this.startTime = startTime;
    }

    public InstantFilter getEndTime() {
        return endTime;
    }

    public void setEndTime(InstantFilter endTime) {
        this.endTime = endTime;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(LongFilter userGroupId) {
        this.userGroupId = userGroupId;
    }

    public LongFilter getScheduleTypeId() {
        return scheduleTypeId;
    }

    public void setScheduleTypeId(LongFilter scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ScheduleCriteria that = (ScheduleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(startTime, that.startTime) &&
            Objects.equals(endTime, that.endTime) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userGroupId, that.userGroupId) &&
            Objects.equals(scheduleTypeId, that.scheduleTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        startTime,
        endTime,
        userId,
        userGroupId,
        scheduleTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ScheduleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
                (endTime != null ? "endTime=" + endTime + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userGroupId != null ? "userGroupId=" + userGroupId + ", " : "") +
                (scheduleTypeId != null ? "scheduleTypeId=" + scheduleTypeId + ", " : "") +
            "}";
    }

}
