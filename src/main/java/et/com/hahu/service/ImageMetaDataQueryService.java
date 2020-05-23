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

import et.com.hahu.domain.ImageMetaData;
import et.com.hahu.domain.*; // for static metamodels
import et.com.hahu.repository.ImageMetaDataRepository;
import et.com.hahu.service.dto.ImageMetaDataCriteria;
import et.com.hahu.service.dto.ImageMetaDataDTO;
import et.com.hahu.service.mapper.ImageMetaDataMapper;

/**
 * Service for executing complex queries for {@link ImageMetaData} entities in the database.
 * The main input is a {@link ImageMetaDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImageMetaDataDTO} or a {@link Page} of {@link ImageMetaDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImageMetaDataQueryService extends QueryService<ImageMetaData> {

    private final Logger log = LoggerFactory.getLogger(ImageMetaDataQueryService.class);

    private final ImageMetaDataRepository imageMetaDataRepository;

    private final ImageMetaDataMapper imageMetaDataMapper;

    public ImageMetaDataQueryService(ImageMetaDataRepository imageMetaDataRepository, ImageMetaDataMapper imageMetaDataMapper) {
        this.imageMetaDataRepository = imageMetaDataRepository;
        this.imageMetaDataMapper = imageMetaDataMapper;
    }

    /**
     * Return a {@link List} of {@link ImageMetaDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImageMetaDataDTO> findByCriteria(ImageMetaDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ImageMetaData> specification = createSpecification(criteria);
        return imageMetaDataMapper.toDto(imageMetaDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ImageMetaDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImageMetaDataDTO> findByCriteria(ImageMetaDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ImageMetaData> specification = createSpecification(criteria);
        return imageMetaDataRepository.findAll(specification, page)
            .map(imageMetaDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImageMetaDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ImageMetaData> specification = createSpecification(criteria);
        return imageMetaDataRepository.count(specification);
    }

    /**
     * Function to convert {@link ImageMetaDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ImageMetaData> createSpecification(ImageMetaDataCriteria criteria) {
        Specification<ImageMetaData> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ImageMetaData_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ImageMetaData_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ImageMetaData_.value));
            }
            if (criteria.getImageId() != null) {
                specification = specification.and(buildSpecification(criteria.getImageId(),
                    root -> root.join(ImageMetaData_.image, JoinType.LEFT).get(Image_.id)));
            }
        }
        return specification;
    }
}
