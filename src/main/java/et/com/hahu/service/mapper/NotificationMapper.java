package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserGroupMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "userGroup.id", target = "userGroupId")
    @Mapping(source = "userGroup.name", target = "userGroupName")
    NotificationDTO toDto(Notification notification);

    @Mapping(target = "notificationMetaData", ignore = true)
    @Mapping(target = "removeNotificationMetaData", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "userGroupId", target = "userGroup")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
