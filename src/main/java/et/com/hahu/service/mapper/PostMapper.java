package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.PostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class, TagMapper.class})
public interface PostMapper extends EntityMapper<PostDTO, Post> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PostDTO toDto(Post post);

    @Mapping(target = "postMetaData", ignore = true)
    @Mapping(target = "removePostMetaData", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "removeComment", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "removeLike", ignore = true)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "removeViews", ignore = true)
    @Mapping(target = "shares", ignore = true)
    @Mapping(target = "removeShares", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeCategory", ignore = true)
    @Mapping(target = "removeTag", ignore = true)
    Post toEntity(PostDTO postDTO);

    default Post fromId(Long id) {
        if (id == null) {
            return null;
        }
        Post post = new Post();
        post.setId(id);
        return post;
    }
}
