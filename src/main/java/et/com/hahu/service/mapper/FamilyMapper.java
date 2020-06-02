package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.FamilyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Family} and its DTO {@link FamilyDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FamilyMapper extends EntityMapper<FamilyDTO, Family> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    FamilyDTO toDto(Family family);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeParent", ignore = true)
    Family toEntity(FamilyDTO familyDTO);

    default Family fromId(Long id) {
        if (id == null) {
            return null;
        }
        Family family = new Family();
        family.setId(id);
        return family;
    }
}
