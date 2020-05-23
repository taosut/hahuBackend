package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.ScheduleTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScheduleType} and its DTO {@link ScheduleTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScheduleTypeMapper extends EntityMapper<ScheduleTypeDTO, ScheduleType> {


    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedule", ignore = true)
    ScheduleType toEntity(ScheduleTypeDTO scheduleTypeDTO);

    default ScheduleType fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScheduleType scheduleType = new ScheduleType();
        scheduleType.setId(id);
        return scheduleType;
    }
}
