package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.SchoolProgressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchoolProgress} and its DTO {@link SchoolProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserGroupMapper.class})
public interface SchoolProgressMapper extends EntityMapper<SchoolProgressDTO, SchoolProgress> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "userGroup.id", target = "userGroupId")
    @Mapping(source = "userGroup.name", target = "userGroupName")
    SchoolProgressDTO toDto(SchoolProgress schoolProgress);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "userGroupId", target = "userGroup")
    SchoolProgress toEntity(SchoolProgressDTO schoolProgressDTO);

    default SchoolProgress fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchoolProgress schoolProgress = new SchoolProgress();
        schoolProgress.setId(id);
        return schoolProgress;
    }
}
