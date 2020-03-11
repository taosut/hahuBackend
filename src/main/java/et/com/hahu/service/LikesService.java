package et.com.hahu.service;

import et.com.hahu.service.dto.LikesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.Likes}.
 */
public interface LikesService {

    /**
     * Save a likes.
     *
     * @param likesDTO the entity to save.
     * @return the persisted entity.
     */
    LikesDTO save(LikesDTO likesDTO);

    /**
     * Get all the likes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LikesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" likes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikesDTO> findOne(Long id);

    /**
     * Delete the "id" likes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
