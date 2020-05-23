package et.com.hahu.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link et.com.hahu.domain.Schedule} entity.
 */
public class ScheduleDTO implements Serializable {
    
    private Long id;

    private String title;

    private Instant startTime;

    private Instant endTime;


    private Long userId;

    private String userLogin;

    private Long userGroupId;

    private String userGroupName;

    private Long scheduleTypeId;

    private String scheduleTypeName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public Long getScheduleTypeId() {
        return scheduleTypeId;
    }

    public void setScheduleTypeId(Long scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }

    public String getScheduleTypeName() {
        return scheduleTypeName;
    }

    public void setScheduleTypeName(String scheduleTypeName) {
        this.scheduleTypeName = scheduleTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScheduleDTO scheduleDTO = (ScheduleDTO) o;
        if (scheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", userGroupId=" + getUserGroupId() +
            ", userGroupName='" + getUserGroupName() + "'" +
            ", scheduleTypeId=" + getScheduleTypeId() +
            ", scheduleTypeName='" + getScheduleTypeName() + "'" +
            "}";
    }
}
