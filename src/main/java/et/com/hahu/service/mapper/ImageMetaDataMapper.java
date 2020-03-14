package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.ImageMetaDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImageMetaData} and its DTO {@link ImageMetaDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface ImageMetaDataMapper extends EntityMapper<ImageMetaDataDTO, ImageMetaData> {

    @Mapping(source = "image.id", target = "imageId")
    ImageMetaDataDTO toDto(ImageMetaData imageMetaData);

    @Mapping(source = "imageId", target = "image")
    ImageMetaData toEntity(ImageMetaDataDTO imageMetaDataDTO);

    default ImageMetaData fromId(Long id) {
        if (id == null) {
            return null;
        }
        ImageMetaData imageMetaData = new ImageMetaData();
        imageMetaData.setId(id);
        return imageMetaData;
    }
}
