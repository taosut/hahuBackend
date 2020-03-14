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

import et.com.hahu.domain.UserGroup;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.UserGroupRepository;
import et.com.hahu.service.dto.UserGroupCriteria;
import et.com.hahu.service.dto.UserGroupDTO;
import et.com.hahu.service.mapper.UserGroupMapper;

/**
 * Service for executing complex queries for {@link UserGroup} entities in the database.
 * The main input is a {@link UserGroupCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserGroupDTO} or a {@link Page} of {@link UserGroupDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserGroupQueryService extends QueryService<UserGroup> {

    private final Logger log = LoggerFactory.getLogger(UserGroupQueryService.class);

    private final UserGroupRepository userGroupRepository;

    private final UserGroupMapper userGroupMapper;

    public UserGroupQueryService(UserGroupRepository userGroupRepository, UserGroupMapper userGroupMapper) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupMapper = userGroupMapper;
    }

    /**
     * Return a {@link List} of {@link UserGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserGroupDTO> findByCriteria(UserGroupCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserGroup> specification = createSpecification(criteria);
        return userGroupMapper.toDto(userGroupRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserGroupDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserGroupDTO> findByCriteria(UserGroupCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserGroup> specification = createSpecification(criteria);
        return userGroupRepository.findAll(specification, page)
            .map(userGroupMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserGroupCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserGroup> specification = createSpecification(criteria);
        return userGroupRepository.count(specification);
    }

    /**
     * Function to convert {@link UserGroupCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserGroup> createSpecification(UserGroupCriteria criteria) {
        Specification<UserGroup> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserGroup_.id));
            }
            if (criteria.getGroupName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupName(), UserGroup_.groupName));
            }
            if (criteria.getOwner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOwner(), UserGroup_.owner));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserGroup_.users, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
