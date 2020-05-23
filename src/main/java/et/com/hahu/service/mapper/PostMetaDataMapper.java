package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.PostMetaDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PostMetaData} and its DTO {@link PostMetaDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {PostMapper.class})
public interface PostMetaDataMapper extends EntityMapper<PostMetaDataDTO, PostMetaData> {

    @Mapping(source = "post.id", target = "postId")
    PostMetaDataDTO toDto(PostMetaData postMetaData);

    @Mapping(source = "postId", target = "post")
    PostMetaData toEntity(PostMetaDataDTO postMetaDataDTO);

    default PostMetaData fromId(Long id) {
        if (id == null) {
            return null;
        }
        PostMetaData postMetaData = new PostMetaData();
        postMetaData.setId(id);
        return postMetaData;
    }
}
