package et.com.hahu.service;

import et.com.hahu.service.dto.NotificationMetaDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.NotificationMetaData}.
 */
public interface NotificationMetaDataService {

    /**
     * Save a notificationMetaData.
     *
     * @param notificationMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    NotificationMetaDataDTO save(NotificationMetaDataDTO notificationMetaDataDTO);

    /**
     * Get all the notificationMetaData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NotificationMetaDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" notificationMetaData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotificationMetaDataDTO> findOne(Long id);

    /**
     * Delete the "id" notificationMetaData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
