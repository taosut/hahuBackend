package et.com.hahu.service;

import et.com.hahu.service.dto.SettingDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.Setting}.
 */
public interface SettingService {

    /**
     * Save a setting.
     *
     * @param settingDTO the entity to save.
     * @return the persisted entity.
     */
    SettingDTO save(SettingDTO settingDTO);

    /**
     * Get all the settings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SettingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" setting.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SettingDTO> findOne(Long id);

    /**
     * Delete the "id" setting.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
