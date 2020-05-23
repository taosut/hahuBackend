package et.com.hahu.service;

import et.com.hahu.service.dto.SchoolProgressDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.SchoolProgress}.
 */
public interface SchoolProgressService {

    /**
     * Save a schoolProgress.
     *
     * @param schoolProgressDTO the entity to save.
     * @return the persisted entity.
     */
    SchoolProgressDTO save(SchoolProgressDTO schoolProgressDTO);

    /**
     * Get all the schoolProgresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SchoolProgressDTO> findAll(Pageable pageable);


    /**
     * Get the "id" schoolProgress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchoolProgressDTO> findOne(Long id);

    /**
     * Delete the "id" schoolProgress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
