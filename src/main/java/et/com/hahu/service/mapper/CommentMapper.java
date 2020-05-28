package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PostMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "comment.id", target = "commentId")
    CommentDTO toDto(Comment comment);

    @Mapping(target = "replies", ignore = true)
    @Mapping(target = "removeReply", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "removeLike", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "postId", target = "post")
    @Mapping(source = "commentId", target = "comment")
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
