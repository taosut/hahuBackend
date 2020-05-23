package et.com.hahu.service.impl;

import et.com.hahu.service.NotificationMetaDataService;
import et.com.hahu.domain.NotificationMetaData;
import et.com.hahu.repository.NotificationMetaDataRepository;
import et.com.hahu.service.dto.NotificationMetaDataDTO;
import et.com.hahu.service.mapper.NotificationMetaDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NotificationMetaData}.
 */
@Service
@Transactional
public class NotificationMetaDataServiceImpl implements NotificationMetaDataService {

    private final Logger log = LoggerFactory.getLogger(NotificationMetaDataServiceImpl.class);

    private final NotificationMetaDataRepository notificationMetaDataRepository;

    private final NotificationMetaDataMapper notificationMetaDataMapper;

    public NotificationMetaDataServiceImpl(NotificationMetaDataRepository notificationMetaDataRepository, NotificationMetaDataMapper notificationMetaDataMapper) {
        this.notificationMetaDataRepository = notificationMetaDataRepository;
        this.notificationMetaDataMapper = notificationMetaDataMapper;
    }

    /**
     * Save a notificationMetaData.
     *
     * @param notificationMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NotificationMetaDataDTO save(NotificationMetaDataDTO notificationMetaDataDTO) {
        log.debug("Request to save NotificationMetaData : {}", notificationMetaDataDTO);
        NotificationMetaData notificationMetaData = notificationMetaDataMapper.toEntity(notificationMetaDataDTO);
        notificationMetaData = notificationMetaDataRepository.save(notificationMetaData);
        return notificationMetaDataMapper.toDto(notificationMetaData);
    }

    /**
     * Get all the notificationMetaData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NotificationMetaDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationMetaData");
        return notificationMetaDataRepository.findAll(pageable)
            .map(notificationMetaDataMapper::toDto);
    }


    /**
     * Get one notificationMetaData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NotificationMetaDataDTO> findOne(Long id) {
        log.debug("Request to get NotificationMetaData : {}", id);
        return notificationMetaDataRepository.findById(id)
            .map(notificationMetaDataMapper::toDto);
    }

    /**
     * Delete the notificationMetaData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete NotificationMetaData : {}", id);

        notificationMetaDataRepository.deleteById(id);
    }
}
