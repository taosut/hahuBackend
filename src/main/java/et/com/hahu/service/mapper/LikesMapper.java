package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.LikesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Likes} and its DTO {@link LikesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PostMapper.class, CommentMapper.class})
public interface LikesMapper extends EntityMapper<LikesDTO, Likes> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "comment.id", target = "commentId")
    LikesDTO toDto(Likes likes);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "postId", target = "post")
    @Mapping(source = "commentId", target = "comment")
    Likes toEntity(LikesDTO likesDTO);

    default Likes fromId(Long id) {
        if (id == null) {
            return null;
        }
        Likes likes = new Likes();
        likes.setId(id);
        return likes;
    }
}
