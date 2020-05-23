package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.UserGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGroup} and its DTO {@link UserGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SchoolMapper.class})
public interface UserGroupMapper extends EntityMapper<UserGroupDTO, UserGroup> {

    @Mapping(source = "school.id", target = "schoolId")
    @Mapping(source = "school.name", target = "schoolName")
    UserGroupDTO toDto(UserGroup userGroup);

    @Mapping(target = "notifications", ignore = true)
    @Mapping(target = "removeNotification", ignore = true)
    @Mapping(target = "schedules", ignore = true)
    @Mapping(target = "removeSchedule", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "removeOwner", ignore = true)
    @Mapping(source = "schoolId", target = "school")
    UserGroup toEntity(UserGroupDTO userGroupDTO);

    default UserGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        return userGroup;
    }
}
