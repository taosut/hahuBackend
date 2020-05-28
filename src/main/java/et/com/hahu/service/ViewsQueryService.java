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

import et.com.hahu.domain.Views;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.ViewsRepository;
import et.com.hahu.service.dto.ViewsCriteria;
import et.com.hahu.service.dto.ViewsDTO;
import et.com.hahu.service.mapper.ViewsMapper;

/**
 * Service for executing complex queries for {@link Views} entities in the database.
 * The main input is a {@link ViewsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ViewsDTO} or a {@link Page} of {@link ViewsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ViewsQueryService extends QueryService<Views> {

    private final Logger log = LoggerFactory.getLogger(ViewsQueryService.class);

    private final ViewsRepository viewsRepository;

    private final ViewsMapper viewsMapper;

    public ViewsQueryService(ViewsRepository viewsRepository, ViewsMapper viewsMapper) {
        this.viewsRepository = viewsRepository;
        this.viewsMapper = viewsMapper;
    }

    /**
     * Return a {@link List} of {@link ViewsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ViewsDTO> findByCriteria(ViewsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Views> specification = createSpecification(criteria);
        return viewsMapper.toDto(viewsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ViewsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ViewsDTO> findByCriteria(ViewsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Views> specification = createSpecification(criteria);
        return viewsRepository.findAll(specification, page)
            .map(viewsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViewsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Views> specification = createSpecification(criteria);
        return viewsRepository.count(specification);
    }

    /**
     * Function to convert {@link ViewsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Views> createSpecification(ViewsCriteria criteria) {
        Specification<Views> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Views_.id));
            }
            if (criteria.getRegisteredTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisteredTime(), Views_.registeredTime));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Views_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Views_.post, JoinType.LEFT).get(Post_.id)));
            }
        }
        return specification;
    }
}
