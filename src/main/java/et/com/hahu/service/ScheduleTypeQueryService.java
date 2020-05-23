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

import et.com.hahu.domain.ScheduleType;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.ScheduleTypeRepository;
import et.com.hahu.service.dto.ScheduleTypeCriteria;
import et.com.hahu.service.dto.ScheduleTypeDTO;
import et.com.hahu.service.mapper.ScheduleTypeMapper;

/**
 * Service for executing complex queries for {@link ScheduleType} entities in the database.
 * The main input is a {@link ScheduleTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScheduleTypeDTO} or a {@link Page} of {@link ScheduleTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScheduleTypeQueryService extends QueryService<ScheduleType> {

    private final Logger log = LoggerFactory.getLogger(ScheduleTypeQueryService.class);

    private final ScheduleTypeRepository scheduleTypeRepository;

    private final ScheduleTypeMapper scheduleTypeMapper;

    public ScheduleTypeQueryService(ScheduleTypeRepository scheduleTypeRepository, ScheduleTypeMapper scheduleTypeMapper) {
        this.scheduleTypeRepository = scheduleTypeRepository;
        this.scheduleTypeMapper = scheduleTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ScheduleTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScheduleTypeDTO> findByCriteria(ScheduleTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ScheduleType> specification = createSpecification(criteria);
        return scheduleTypeMapper.toDto(scheduleTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScheduleTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScheduleTypeDTO> findByCriteria(ScheduleTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ScheduleType> specification = createSpecification(criteria);
        return scheduleTypeRepository.findAll(specification, page)
            .map(scheduleTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScheduleTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ScheduleType> specification = createSpecification(criteria);
        return scheduleTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ScheduleTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ScheduleType> createSpecification(ScheduleTypeCriteria criteria) {
        Specification<ScheduleType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ScheduleType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ScheduleType_.name));
            }
            if (criteria.getScheduleId() != null) {
                specification = specification.and(buildSpecification(criteria.getScheduleId(),
                    root -> root.join(ScheduleType_.schedules, JoinType.LEFT).get(Schedule_.id)));
            }
        }
        return specification;
    }
}
