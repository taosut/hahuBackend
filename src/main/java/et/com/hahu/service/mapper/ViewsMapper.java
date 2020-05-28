package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.ViewsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Views} and its DTO {@link ViewsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, PostMapper.class})
public interface ViewsMapper extends EntityMapper<ViewsDTO, Views> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "post.id", target = "postId")
    ViewsDTO toDto(Views views);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "postId", target = "post")
    Views toEntity(ViewsDTO viewsDTO);

    default Views fromId(Long id) {
        if (id == null) {
            return null;
        }
        Views views = new Views();
        views.setId(id);
        return views;
    }
}
