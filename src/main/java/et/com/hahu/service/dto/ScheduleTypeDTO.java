package et.com.hahu.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link et.com.hahu.domain.ScheduleType} entity.
 */
public class ScheduleTypeDTO implements Serializable {
    
    private Long id;

    private String name;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScheduleTypeDTO scheduleTypeDTO = (ScheduleTypeDTO) o;
        if (scheduleTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scheduleTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScheduleTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
