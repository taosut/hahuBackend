package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.PreferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Preference} and its DTO {@link PreferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PreferenceMapper extends EntityMapper<PreferenceDTO, Preference> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PreferenceDTO toDto(Preference preference);

    @Mapping(source = "userId", target = "user")
    Preference toEntity(PreferenceDTO preferenceDTO);

    default Preference fromId(Long id) {
        if (id == null) {
            return null;
        }
        Preference preference = new Preference();
        preference.setId(id);
        return preference;
    }
}
