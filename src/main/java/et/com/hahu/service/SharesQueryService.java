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

import et.com.hahu.domain.Shares;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.SharesRepository;
import et.com.hahu.service.dto.SharesCriteria;
import et.com.hahu.service.dto.SharesDTO;
import et.com.hahu.service.mapper.SharesMapper;

/**
 * Service for executing complex queries for {@link Shares} entities in the database.
 * The main input is a {@link SharesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SharesDTO} or a {@link Page} of {@link SharesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SharesQueryService extends QueryService<Shares> {

    private final Logger log = LoggerFactory.getLogger(SharesQueryService.class);

    private final SharesRepository sharesRepository;

    private final SharesMapper sharesMapper;

    public SharesQueryService(SharesRepository sharesRepository, SharesMapper sharesMapper) {
        this.sharesRepository = sharesRepository;
        this.sharesMapper = sharesMapper;
    }

    /**
     * Return a {@link List} of {@link SharesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SharesDTO> findByCriteria(SharesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Shares> specification = createSpecification(criteria);
        return sharesMapper.toDto(sharesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SharesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SharesDTO> findByCriteria(SharesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Shares> specification = createSpecification(criteria);
        return sharesRepository.findAll(specification, page)
            .map(sharesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SharesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Shares> specification = createSpecification(criteria);
        return sharesRepository.count(specification);
    }

    /**
     * Function to convert {@link SharesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Shares> createSpecification(SharesCriteria criteria) {
        Specification<Shares> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Shares_.id));
            }
            if (criteria.getRegisteredTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisteredTime(), Shares_.registeredTime));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Shares_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Shares_.post, JoinType.LEFT).get(Post_.id)));
            }
        }
        return specification;
    }
}
