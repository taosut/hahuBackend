package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.SettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Setting} and its DTO {@link SettingDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SettingMapper extends EntityMapper<SettingDTO, Setting> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    SettingDTO toDto(Setting setting);

    @Mapping(source = "userId", target = "user")
    Setting toEntity(SettingDTO settingDTO);

    default Setting fromId(Long id) {
        if (id == null) {
            return null;
        }
        Setting setting = new Setting();
        setting.setId(id);
        return setting;
    }
}
