package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.SchoolProgressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchoolProgress} and its DTO {@link SchoolProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SchoolProgressMapper extends EntityMapper<SchoolProgressDTO, SchoolProgress> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    SchoolProgressDTO toDto(SchoolProgress schoolProgress);

    @Mapping(source = "userId", target = "user")
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
