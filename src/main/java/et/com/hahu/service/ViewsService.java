package et.com.hahu.service;

import et.com.hahu.service.dto.ViewsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.Views}.
 */
public interface ViewsService {

    /**
     * Save a views.
     *
     * @param viewsDTO the entity to save.
     * @return the persisted entity.
     */
    ViewsDTO save(ViewsDTO viewsDTO);

    /**
     * Get all the views.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ViewsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" views.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ViewsDTO> findOne(Long id);

    /**
     * Delete the "id" views.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
