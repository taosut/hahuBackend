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

import et.com.hahu.domain.Likes;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.LikesRepository;
import et.com.hahu.service.dto.LikesCriteria;
import et.com.hahu.service.dto.LikesDTO;
import et.com.hahu.service.mapper.LikesMapper;

/**
 * Service for executing complex queries for {@link Likes} entities in the database.
 * The main input is a {@link LikesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LikesDTO} or a {@link Page} of {@link LikesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LikesQueryService extends QueryService<Likes> {

    private final Logger log = LoggerFactory.getLogger(LikesQueryService.class);

    private final LikesRepository likesRepository;

    private final LikesMapper likesMapper;

    public LikesQueryService(LikesRepository likesRepository, LikesMapper likesMapper) {
        this.likesRepository = likesRepository;
        this.likesMapper = likesMapper;
    }

    /**
     * Return a {@link List} of {@link LikesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LikesDTO> findByCriteria(LikesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Likes> specification = createSpecification(criteria);
        return likesMapper.toDto(likesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LikesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LikesDTO> findByCriteria(LikesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Likes> specification = createSpecification(criteria);
        return likesRepository.findAll(specification, page)
            .map(likesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LikesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Likes> specification = createSpecification(criteria);
        return likesRepository.count(specification);
    }

    /**
     * Function to convert {@link LikesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Likes> createSpecification(LikesCriteria criteria) {
        Specification<Likes> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Likes_.id));
            }
            if (criteria.getRegisteredTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisteredTime(), Likes_.registeredTime));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Likes_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Likes_.post, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentId(),
                    root -> root.join(Likes_.comment, JoinType.LEFT).get(Comment_.id)));
            }
        }
        return specification;
    }
}
