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

import et.com.hahu.domain.Schedule;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.ScheduleRepository;
import et.com.hahu.service.dto.ScheduleCriteria;
import et.com.hahu.service.dto.ScheduleDTO;
import et.com.hahu.service.mapper.ScheduleMapper;

/**
 * Service for executing complex queries for {@link Schedule} entities in the database.
 * The main input is a {@link ScheduleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ScheduleDTO} or a {@link Page} of {@link ScheduleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ScheduleQueryService extends QueryService<Schedule> {

    private final Logger log = LoggerFactory.getLogger(ScheduleQueryService.class);

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    public ScheduleQueryService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
    }

    /**
     * Return a {@link List} of {@link ScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ScheduleDTO> findByCriteria(ScheduleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Schedule> specification = createSpecification(criteria);
        return scheduleMapper.toDto(scheduleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ScheduleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ScheduleDTO> findByCriteria(ScheduleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Schedule> specification = createSpecification(criteria);
        return scheduleRepository.findAll(specification, page)
            .map(scheduleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ScheduleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Schedule> specification = createSpecification(criteria);
        return scheduleRepository.count(specification);
    }

    /**
     * Function to convert {@link ScheduleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Schedule> createSpecification(ScheduleCriteria criteria) {
        Specification<Schedule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Schedule_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Schedule_.title));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartTime(), Schedule_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndTime(), Schedule_.endTime));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Schedule_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUserGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserGroupId(),
                    root -> root.join(Schedule_.userGroup, JoinType.LEFT).get(UserGroup_.id)));
            }
        }
        return specification;
    }
}
