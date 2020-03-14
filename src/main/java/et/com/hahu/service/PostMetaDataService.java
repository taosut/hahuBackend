package et.com.hahu.service;

import et.com.hahu.service.dto.PostMetaDataDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.PostMetaData}.
 */
public interface PostMetaDataService {

    /**
     * Save a postMetaData.
     *
     * @param postMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    PostMetaDataDTO save(PostMetaDataDTO postMetaDataDTO);

    /**
     * Get all the postMetaData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PostMetaDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" postMetaData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostMetaDataDTO> findOne(Long id);

    /**
     * Delete the "id" postMetaData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
