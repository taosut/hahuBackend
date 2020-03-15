package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.SchoolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link School} and its DTO {@link SchoolDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SchoolMapper extends EntityMapper<SchoolDTO, School> {


    @Mapping(target = "userGroups", ignore = true)
    @Mapping(target = "removeUserGroup", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
    School toEntity(SchoolDTO schoolDTO);

    default School fromId(Long id) {
        if (id == null) {
            return null;
        }
        School school = new School();
        school.setId(id);
        return school;
    }
}
