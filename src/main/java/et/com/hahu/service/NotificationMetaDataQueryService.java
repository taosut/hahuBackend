package et.com.hahu.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import et.com.hahu.domain.NotificationMetaData;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.NotificationMetaDataRepository;
import et.com.hahu.service.dto.NotificationMetaDataCriteria;
import et.com.hahu.service.dto.NotificationMetaDataDTO;
import et.com.hahu.service.mapper.NotificationMetaDataMapper;

/**
 * Service for executing complex queries for {@link NotificationMetaData} entities in the database.
 * The main input is a {@link NotificationMetaDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationMetaDataDTO} or a {@link Page} of {@link NotificationMetaDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationMetaDataQueryService extends QueryService<NotificationMetaData> {

    private final Logger log = LoggerFactory.getLogger(NotificationMetaDataQueryService.class);

    private final NotificationMetaDataRepository notificationMetaDataRepository;

    private final NotificationMetaDataMapper notificationMetaDataMapper;

    public NotificationMetaDataQueryService(NotificationMetaDataRepository notificationMetaDataRepository, NotificationMetaDataMapper notificationMetaDataMapper) {
        this.notificationMetaDataRepository = notificationMetaDataRepository;
        this.notificationMetaDataMapper = notificationMetaDataMapper;
    }

    /**
     * Return a {@link List} of {@link NotificationMetaDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationMetaDataDTO> findByCriteria(NotificationMetaDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationMetaData> specification = createSpecification(criteria);
        return notificationMetaDataMapper.toDto(notificationMetaDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotificationMetaDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationMetaDataDTO> findByCriteria(NotificationMetaDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationMetaData> specification = createSpecification(criteria);
        return notificationMetaDataRepository.findAll(specification, page)
            .map(notificationMetaDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationMetaDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationMetaData> specification = createSpecification(criteria);
        return notificationMetaDataRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationMetaDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationMetaData> createSpecification(NotificationMetaDataCriteria criteria) {
        Specification<NotificationMetaData> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationMetaData_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NotificationMetaData_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), NotificationMetaData_.value));
            }
            if (criteria.getNotificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getNotificationId(),
                    root -> root.join(NotificationMetaData_.notification, JoinType.LEFT).get(Notification_.id)));
            }
        }
        return specification;
    }
}
