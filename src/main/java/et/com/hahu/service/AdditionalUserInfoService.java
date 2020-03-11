package et.com.hahu.service;

import et.com.hahu.service.dto.AdditionalUserInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.AdditionalUserInfo}.
 */
public interface AdditionalUserInfoService {

    /**
     * Save a additionalUserInfo.
     *
     * @param additionalUserInfoDTO the entity to save.
     * @return the persisted entity.
     */
    AdditionalUserInfoDTO save(AdditionalUserInfoDTO additionalUserInfoDTO);

    /**
     * Get all the additionalUserInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdditionalUserInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" additionalUserInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdditionalUserInfoDTO> findOne(Long id);

    /**
     * Delete the "id" additionalUserInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
