package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.UserGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGroup} and its DTO {@link UserGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserGroupMapper extends EntityMapper<UserGroupDTO, UserGroup> {


    @Mapping(target = "removeUser", ignore = true)
    @Mapping(target = "removeOwner", ignore = true)

    default UserGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        return userGroup;
    }
}
