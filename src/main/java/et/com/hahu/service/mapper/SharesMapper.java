package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.SharesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shares} and its DTO {@link SharesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PostMapper.class})
public interface SharesMapper extends EntityMapper<SharesDTO, Shares> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "post.id", target = "postId")
    SharesDTO toDto(Shares shares);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "postId", target = "post")
    Shares toEntity(SharesDTO sharesDTO);

    default Shares fromId(Long id) {
        if (id == null) {
            return null;
        }
        Shares shares = new Shares();
        shares.setId(id);
        return shares;
    }
}
