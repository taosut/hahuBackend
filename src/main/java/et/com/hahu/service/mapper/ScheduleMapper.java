package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.ScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Schedule} and its DTO {@link ScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserGroupMapper.class, ScheduleTypeMapper.class})
public interface ScheduleMapper extends EntityMapper<ScheduleDTO, Schedule> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "userGroup.id", target = "userGroupId")
    @Mapping(source = "userGroup.name", target = "userGroupName")
    @Mapping(source = "scheduleType.id", target = "scheduleTypeId")
    @Mapping(source = "scheduleType.name", target = "scheduleTypeName")
    ScheduleDTO toDto(Schedule schedule);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "userGroupId", target = "userGroup")
    @Mapping(source = "scheduleTypeId", target = "scheduleType")
    Schedule toEntity(ScheduleDTO scheduleDTO);

    default Schedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        Schedule schedule = new Schedule();
        schedule.setId(id);
        return schedule;
    }
}
