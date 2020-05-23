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

import et.com.hahu.domain.PostMetaData;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.PostMetaDataRepository;
import et.com.hahu.service.dto.PostMetaDataCriteria;
import et.com.hahu.service.dto.PostMetaDataDTO;
import et.com.hahu.service.mapper.PostMetaDataMapper;

/**
 * Service for executing complex queries for {@link PostMetaData} entities in the database.
 * The main input is a {@link PostMetaDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostMetaDataDTO} or a {@link Page} of {@link PostMetaDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostMetaDataQueryService extends QueryService<PostMetaData> {

    private final Logger log = LoggerFactory.getLogger(PostMetaDataQueryService.class);

    private final PostMetaDataRepository postMetaDataRepository;

    private final PostMetaDataMapper postMetaDataMapper;

    public PostMetaDataQueryService(PostMetaDataRepository postMetaDataRepository, PostMetaDataMapper postMetaDataMapper) {
        this.postMetaDataRepository = postMetaDataRepository;
        this.postMetaDataMapper = postMetaDataMapper;
    }

    /**
     * Return a {@link List} of {@link PostMetaDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostMetaDataDTO> findByCriteria(PostMetaDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PostMetaData> specification = createSpecification(criteria);
        return postMetaDataMapper.toDto(postMetaDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostMetaDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostMetaDataDTO> findByCriteria(PostMetaDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PostMetaData> specification = createSpecification(criteria);
        return postMetaDataRepository.findAll(specification, page)
            .map(postMetaDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostMetaDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PostMetaData> specification = createSpecification(criteria);
        return postMetaDataRepository.count(specification);
    }

    /**
     * Function to convert {@link PostMetaDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PostMetaData> createSpecification(PostMetaDataCriteria criteria) {
        Specification<PostMetaData> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PostMetaData_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PostMetaData_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), PostMetaData_.value));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(PostMetaData_.post, JoinType.LEFT).get(Post_.id)));
            }
        }
        return specification;
    }
}
