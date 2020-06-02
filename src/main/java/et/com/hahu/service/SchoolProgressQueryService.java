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

import et.com.hahu.domain.SchoolProgress;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.SchoolProgressRepository;
import et.com.hahu.service.dto.SchoolProgressCriteria;
import et.com.hahu.service.dto.SchoolProgressDTO;
import et.com.hahu.service.mapper.SchoolProgressMapper;

/**
 * Service for executing complex queries for {@link SchoolProgress} entities in the database.
 * The main input is a {@link SchoolProgressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SchoolProgressDTO} or a {@link Page} of {@link SchoolProgressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SchoolProgressQueryService extends QueryService<SchoolProgress> {

    private final Logger log = LoggerFactory.getLogger(SchoolProgressQueryService.class);

    private final SchoolProgressRepository schoolProgressRepository;

    private final SchoolProgressMapper schoolProgressMapper;

    public SchoolProgressQueryService(SchoolProgressRepository schoolProgressRepository, SchoolProgressMapper schoolProgressMapper) {
        this.schoolProgressRepository = schoolProgressRepository;
        this.schoolProgressMapper = schoolProgressMapper;
    }

    /**
     * Return a {@link List} of {@link SchoolProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SchoolProgressDTO> findByCriteria(SchoolProgressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SchoolProgress> specification = createSpecification(criteria);
        return schoolProgressMapper.toDto(schoolProgressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SchoolProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SchoolProgressDTO> findByCriteria(SchoolProgressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchoolProgress> specification = createSpecification(criteria);
        return schoolProgressRepository.findAll(specification, page)
            .map(schoolProgressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SchoolProgressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SchoolProgress> specification = createSpecification(criteria);
        return schoolProgressRepository.count(specification);
    }

    /**
     * Function to convert {@link SchoolProgressCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SchoolProgress> createSpecification(SchoolProgressCriteria criteria) {
        Specification<SchoolProgress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SchoolProgress_.id));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), SchoolProgress_.subject));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), SchoolProgress_.year));
            }
            if (criteria.getSemester() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSemester(), SchoolProgress_.semester));
            }
            if (criteria.getResult() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResult(), SchoolProgress_.result));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(SchoolProgress_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getUserGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserGroupId(),
                    root -> root.join(SchoolProgress_.userGroup, JoinType.LEFT).get(UserGroup_.id)));
            }
        }
        return specification;
    }
}
