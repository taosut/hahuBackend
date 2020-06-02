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

import et.com.hahu.domain.Family;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.FamilyRepository;
import et.com.hahu.service.dto.FamilyCriteria;
import et.com.hahu.service.dto.FamilyDTO;
import et.com.hahu.service.mapper.FamilyMapper;

/**
 * Service for executing complex queries for {@link Family} entities in the database.
 * The main input is a {@link FamilyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FamilyDTO} or a {@link Page} of {@link FamilyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FamilyQueryService extends QueryService<Family> {

    private final Logger log = LoggerFactory.getLogger(FamilyQueryService.class);

    private final FamilyRepository familyRepository;

    private final FamilyMapper familyMapper;

    public FamilyQueryService(FamilyRepository familyRepository, FamilyMapper familyMapper) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
    }

    /**
     * Return a {@link List} of {@link FamilyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FamilyDTO> findByCriteria(FamilyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Family> specification = createSpecification(criteria);
        return familyMapper.toDto(familyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FamilyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FamilyDTO> findByCriteria(FamilyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Family> specification = createSpecification(criteria);
        return familyRepository.findAll(specification, page)
            .map(familyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FamilyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Family> specification = createSpecification(criteria);
        return familyRepository.count(specification);
    }

    /**
     * Function to convert {@link FamilyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Family> createSpecification(FamilyCriteria criteria) {
        Specification<Family> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Family_.id));
            }
            if (criteria.getHasFamilyControl() != null) {
                specification = specification.and(buildSpecification(criteria.getHasFamilyControl(), Family_.hasFamilyControl));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Family_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getParentId() != null) {
                specification = specification.and(buildSpecification(criteria.getParentId(),
                    root -> root.join(Family_.parents, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
