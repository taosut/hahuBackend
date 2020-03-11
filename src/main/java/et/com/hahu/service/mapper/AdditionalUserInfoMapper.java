package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.AdditionalUserInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdditionalUserInfo} and its DTO {@link AdditionalUserInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserGroupMapper.class})
public interface AdditionalUserInfoMapper extends EntityMapper<AdditionalUserInfoDTO, AdditionalUserInfo> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "userGroup.id", target = "userGroupId")
    AdditionalUserInfoDTO toDto(AdditionalUserInfo additionalUserInfo);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "followings", ignore = true)
    @Mapping(target = "removeFollowing", ignore = true)
    @Mapping(target = "followers", ignore = true)
    @Mapping(target = "removeFollower", ignore = true)
    @Mapping(source = "userGroupId", target = "userGroup")
    AdditionalUserInfo toEntity(AdditionalUserInfoDTO additionalUserInfoDTO);

    default AdditionalUserInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        AdditionalUserInfo additionalUserInfo = new AdditionalUserInfo();
        additionalUserInfo.setId(id);
        return additionalUserInfo;
    }
}
