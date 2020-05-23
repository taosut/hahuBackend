package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.AlbumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Album} and its DTO {@link AlbumDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AlbumMapper extends EntityMapper<AlbumDTO, Album> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    AlbumDTO toDto(Album album);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImage", ignore = true)
    @Mapping(source = "userId", target = "user")
    Album toEntity(AlbumDTO albumDTO);

    default Album fromId(Long id) {
        if (id == null) {
            return null;
        }
        Album album = new Album();
        album.setId(id);
        return album;
    }
}
