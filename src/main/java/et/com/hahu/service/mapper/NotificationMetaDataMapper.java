package et.com.hahu.service.mapper;


import et.com.hahu.domain.*;
import et.com.hahu.service.dto.NotificationMetaDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificationMetaData} and its DTO {@link NotificationMetaDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {NotificationMapper.class})
public interface NotificationMetaDataMapper extends EntityMapper<NotificationMetaDataDTO, NotificationMetaData> {

    @Mapping(source = "notification.id", target = "notificationId")
    NotificationMetaDataDTO toDto(NotificationMetaData notificationMetaData);

    @Mapping(source = "notificationId", target = "notification")
    NotificationMetaData toEntity(NotificationMetaDataDTO notificationMetaDataDTO);

    default NotificationMetaData fromId(Long id) {
        if (id == null) {
            return null;
        }
        NotificationMetaData notificationMetaData = new NotificationMetaData();
        notificationMetaData.setId(id);
        return notificationMetaData;
    }
}
