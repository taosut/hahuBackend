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

import et.com.hahu.domain.Comment;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.CommentRepository;
import et.com.hahu.service.dto.CommentCriteria;
import et.com.hahu.service.dto.CommentDTO;
import et.com.hahu.service.mapper.CommentMapper;

/**
 * Service for executing complex queries for {@link Comment} entities in the database.
 * The main input is a {@link CommentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommentDTO} or a {@link Page} of {@link CommentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommentQueryService extends QueryService<Comment> {

    private final Logger log = LoggerFactory.getLogger(CommentQueryService.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentQueryService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Return a {@link List} of {@link CommentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommentDTO> findByCriteria(CommentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Comment> specification = createSpecification(criteria);
        return commentMapper.toDto(commentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommentDTO> findByCriteria(CommentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Comment> specification = createSpecification(criteria);
        return commentRepository.findAll(specification, page)
            .map(commentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Comment> specification = createSpecification(criteria);
        return commentRepository.count(specification);
    }

    /**
     * Function to convert {@link CommentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Comment> createSpecification(CommentCriteria criteria) {
        Specification<Comment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Comment_.id));
            }
            if (criteria.getPostedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostedDate(), Comment_.postedDate));
            }
            if (criteria.getModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedDate(), Comment_.modifiedDate));
            }
            if (criteria.getReplyId() != null) {
                specification = specification.and(buildSpecification(criteria.getReplyId(),
                    root -> root.join(Comment_.replies, JoinType.LEFT).get(Comment_.id)));
            }
            if (criteria.getLikeId() != null) {
                specification = specification.and(buildSpecification(criteria.getLikeId(),
                    root -> root.join(Comment_.likes, JoinType.LEFT).get(Likes_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Comment_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Comment_.post, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentId(),
                    root -> root.join(Comment_.comment, JoinType.LEFT).get(Comment_.id)));
            }
        }
        return specification;
    }
}
