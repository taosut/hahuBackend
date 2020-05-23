package et.com.hahu.repository;

import et.com.hahu.domain.NotificationMetaData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NotificationMetaData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationMetaDataRepository extends JpaRepository<NotificationMetaData, Long>, JpaSpecificationExecutor<NotificationMetaData> {
}
