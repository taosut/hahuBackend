package et.com.hahu.service;

import et.com.hahu.service.dto.ImageMetaDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.ImageMetaData}.
 */
public interface ImageMetaDataService {

    /**
     * Save a imageMetaData.
     *
     * @param imageMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    ImageMetaDataDTO save(ImageMetaDataDTO imageMetaDataDTO);

    /**
     * Get all the imageMetaData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageMetaDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" imageMetaData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImageMetaDataDTO> findOne(Long id);

    /**
     * Delete the "id" imageMetaData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
