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

import et.com.hahu.domain.AdditionalUserInfo;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.AdditionalUserInfoRepository;
import et.com.hahu.service.dto.AdditionalUserInfoCriteria;
import et.com.hahu.service.dto.AdditionalUserInfoDTO;
import et.com.hahu.service.mapper.AdditionalUserInfoMapper;

/**
 * Service for executing complex queries for {@link AdditionalUserInfo} entities in the database.
 * The main input is a {@link AdditionalUserInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AdditionalUserInfoDTO} or a {@link Page} of {@link AdditionalUserInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AdditionalUserInfoQueryService extends QueryService<AdditionalUserInfo> {

    private final Logger log = LoggerFactory.getLogger(AdditionalUserInfoQueryService.class);

    private final AdditionalUserInfoRepository additionalUserInfoRepository;

    private final AdditionalUserInfoMapper additionalUserInfoMapper;

    public AdditionalUserInfoQueryService(AdditionalUserInfoRepository additionalUserInfoRepository, AdditionalUserInfoMapper additionalUserInfoMapper) {
        this.additionalUserInfoRepository = additionalUserInfoRepository;
        this.additionalUserInfoMapper = additionalUserInfoMapper;
    }

    /**
     * Return a {@link List} of {@link AdditionalUserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AdditionalUserInfoDTO> findByCriteria(AdditionalUserInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AdditionalUserInfo> specification = createSpecification(criteria);
        return additionalUserInfoMapper.toDto(additionalUserInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AdditionalUserInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AdditionalUserInfoDTO> findByCriteria(AdditionalUserInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AdditionalUserInfo> specification = createSpecification(criteria);
        return additionalUserInfoRepository.findAll(specification, page)
            .map(additionalUserInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AdditionalUserInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AdditionalUserInfo> specification = createSpecification(criteria);
        return additionalUserInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link AdditionalUserInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AdditionalUserInfo> createSpecification(AdditionalUserInfoCriteria criteria) {
        Specification<AdditionalUserInfo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AdditionalUserInfo_.id));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), AdditionalUserInfo_.phone));
            }
            if (criteria.getProfilePicture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfilePicture(), AdditionalUserInfo_.profilePicture));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(AdditionalUserInfo_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getFollowingId() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowingId(),
                    root -> root.join(AdditionalUserInfo_.followings, JoinType.LEFT).get(UsersConnection_.id)));
            }
            if (criteria.getFollowerId() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowerId(),
                    root -> root.join(AdditionalUserInfo_.followers, JoinType.LEFT).get(UsersConnection_.id)));
            }
            if (criteria.getUserGroupId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserGroupId(),
                    root -> root.join(AdditionalUserInfo_.userGroup, JoinType.LEFT).get(UserGroup_.id)));
            }
        }
        return specification;
    }
}
