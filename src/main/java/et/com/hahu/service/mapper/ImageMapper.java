package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.ImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Image} and its DTO {@link ImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {AlbumMapper.class})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {

    @Mapping(source = "album.id", target = "albumId")
    ImageDTO toDto(Image image);

    @Mapping(target = "imageMetaData", ignore = true)
    @Mapping(target = "removeImageMetaData", ignore = true)
    @Mapping(source = "albumId", target = "album")
    Image toEntity(ImageDTO imageDTO);

    default Image fromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
