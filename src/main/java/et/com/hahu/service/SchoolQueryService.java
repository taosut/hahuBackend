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

import et.com.hahu.domain.School;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.SchoolRepository;
import et.com.hahu.service.dto.SchoolCriteria;
import et.com.hahu.service.dto.SchoolDTO;
import et.com.hahu.service.mapper.SchoolMapper;

/**
 * Service for executing complex queries for {@link School} entities in the database.
 * The main input is a {@link SchoolCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SchoolDTO} or a {@link Page} of {@link SchoolDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SchoolQueryService extends QueryService<School> {

    private final Logger log = LoggerFactory.getLogger(SchoolQueryService.class);

    private final SchoolRepository schoolRepository;

    private final SchoolMapper schoolMapper;

    public SchoolQueryService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    /**
     * Return a {@link List} of {@link SchoolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SchoolDTO> findByCriteria(SchoolCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<School> specification = createSpecification(criteria);
        return schoolMapper.toDto(schoolRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SchoolDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SchoolDTO> findByCriteria(SchoolCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<School> specification = createSpecification(criteria);
        return schoolRepository.findAll(specification, page)
            .map(schoolMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SchoolCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<School> specification = createSpecification(criteria);
        return schoolRepository.count(specification);
    }

    /**
     * Function to convert {@link SchoolCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<School> createSpecification(SchoolCriteria criteria) {
        Specification<School> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), School_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), School_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), School_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), School_.email));
            }
            if (criteria.getWebsite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebsite(), School_.website));
            }
            if (criteria.getAboutType() != null) {
                specification = specification.and(buildSpecification(criteria.getAboutType(), School_.aboutType));
            }
            if (criteria.getLocationType() != null) {
                specification = specification.and(buildSpecification(criteria.getLocationType(), School_.locationType));
            }
            if (criteria.getUserGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserGroupId(),
                    root -> root.join(School_.userGroups, JoinType.LEFT).get(UserGroup_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(School_.users, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
