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

import et.com.hahu.domain.UsersConnection;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.UsersConnectionRepository;
import et.com.hahu.service.dto.UsersConnectionCriteria;
import et.com.hahu.service.dto.UsersConnectionDTO;
import et.com.hahu.service.mapper.UsersConnectionMapper;

/**
 * Service for executing complex queries for {@link UsersConnection} entities in the database.
 * The main input is a {@link UsersConnectionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsersConnectionDTO} or a {@link Page} of {@link UsersConnectionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsersConnectionQueryService extends QueryService<UsersConnection> {

    private final Logger log = LoggerFactory.getLogger(UsersConnectionQueryService.class);

    private final UsersConnectionRepository usersConnectionRepository;

    private final UsersConnectionMapper usersConnectionMapper;

    public UsersConnectionQueryService(UsersConnectionRepository usersConnectionRepository, UsersConnectionMapper usersConnectionMapper) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.usersConnectionMapper = usersConnectionMapper;
    }

    /**
     * Return a {@link List} of {@link UsersConnectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsersConnectionDTO> findByCriteria(UsersConnectionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UsersConnection> specification = createSpecification(criteria);
        return usersConnectionMapper.toDto(usersConnectionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsersConnectionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsersConnectionDTO> findByCriteria(UsersConnectionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsersConnection> specification = createSpecification(criteria);
        return usersConnectionRepository.findAll(specification, page)
            .map(usersConnectionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsersConnectionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UsersConnection> specification = createSpecification(criteria);
        return usersConnectionRepository.count(specification);
    }

    /**
     * Function to convert {@link UsersConnectionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UsersConnection> createSpecification(UsersConnectionCriteria criteria) {
        Specification<UsersConnection> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UsersConnection_.id));
            }
            if (criteria.getRegisteredTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegisteredTime(), UsersConnection_.registeredTime));
            }
            if (criteria.getFollowerId() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowerId(),
                    root -> root.join(UsersConnection_.follower, JoinType.LEFT).get(AdditionalUserInfo_.id)));
            }
            if (criteria.getFollowingId() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowingId(),
                    root -> root.join(UsersConnection_.following, JoinType.LEFT).get(AdditionalUserInfo_.id)));
            }
        }
        return specification;
    }
}
