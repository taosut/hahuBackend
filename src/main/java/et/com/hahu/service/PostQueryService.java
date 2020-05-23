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

import et.com.hahu.domain.Post;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.PostRepository;
import et.com.hahu.service.dto.PostCriteria;
import et.com.hahu.service.dto.PostDTO;
import et.com.hahu.service.mapper.PostMapper;

/**
 * Service for executing complex queries for {@link Post} entities in the database.
 * The main input is a {@link PostCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostDTO} or a {@link Page} of {@link PostDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostQueryService extends QueryService<Post> {

    private final Logger log = LoggerFactory.getLogger(PostQueryService.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostQueryService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * Return a {@link List} of {@link PostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostDTO> findByCriteria(PostCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Post> specification = createSpecification(criteria);
        return postMapper.toDto(postRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findByCriteria(PostCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Post> specification = createSpecification(criteria);
        return postRepository.findAll(specification, page)
            .map(postMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Post> specification = createSpecification(criteria);
        return postRepository.count(specification);
    }

    /**
     * Function to convert {@link PostCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Post> createSpecification(PostCriteria criteria) {
        Specification<Post> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Post_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Post_.title));
            }
            if (criteria.getContentType() != null) {
                specification = specification.and(buildSpecification(criteria.getContentType(), Post_.contentType));
            }
            if (criteria.getPostType() != null) {
                specification = specification.and(buildSpecification(criteria.getPostType(), Post_.postType));
            }
            if (criteria.getPostedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostedDate(), Post_.postedDate));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), Post_.modifiedDate));
            }
            if (criteria.getInstantPostEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstantPostEndDate(), Post_.instantPostEndDate));
            }
            if (criteria.getPostMetaDataId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostMetaDataId(),
                    root -> root.join(Post_.postMetaData, JoinType.LEFT).get(PostMetaData_.id)));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentId(),
                    root -> root.join(Post_.comments, JoinType.LEFT).get(Comment_.id)));
            }
            if (criteria.getLikeId() != null) {
                specification = specification.and(buildSpecification(criteria.getLikeId(),
                    root -> root.join(Post_.likes, JoinType.LEFT).get(Likes_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Post_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(Post_.categories, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getTagId() != null) {
                specification = specification.and(buildSpecification(criteria.getTagId(),
                    root -> root.join(Post_.tags, JoinType.LEFT).get(Tag_.id)));
            }
        }
        return specification;
    }
}
