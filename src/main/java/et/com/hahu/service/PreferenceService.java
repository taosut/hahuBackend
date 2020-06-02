package et.com.hahu.service;

import et.com.hahu.service.dto.PreferenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.Preference}.
 */
public interface PreferenceService {

    /**
     * Save a preference.
     *
     * @param preferenceDTO the entity to save.
     * @return the persisted entity.
     */
    PreferenceDTO save(PreferenceDTO preferenceDTO);

    /**
     * Get all the preferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PreferenceDTO> findAll(Pageable pageable);

    /**
     * Get all the preferences with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<PreferenceDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" preference.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferenceDTO> findOne(Long id);

    /**
     * Delete the "id" preference.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
