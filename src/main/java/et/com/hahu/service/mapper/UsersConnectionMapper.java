package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.UsersConnectionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsersConnection} and its DTO {@link UsersConnectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UsersConnectionMapper extends EntityMapper<UsersConnectionDTO, UsersConnection> {

    @Mapping(source = "follower.id", target = "followerId")
    @Mapping(source = "follower.login", target = "followerLogin")
    @Mapping(source = "following.id", target = "followingId")
    @Mapping(source = "following.login", target = "followingLogin")
    UsersConnectionDTO toDto(UsersConnection usersConnection);

    @Mapping(source = "followerId", target = "follower")
    @Mapping(source = "followingId", target = "following")
    UsersConnection toEntity(UsersConnectionDTO usersConnectionDTO);

    default UsersConnection fromId(Long id) {
        if (id == null) {
            return null;
        }
        UsersConnection usersConnection = new UsersConnection();
        usersConnection.setId(id);
        return usersConnection;
    }
}
