package et.com.hahu.service;

import et.com.hahu.service.dto.SharesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.Shares}.
 */
public interface SharesService {

    /**
     * Save a shares.
     *
     * @param sharesDTO the entity to save.
     * @return the persisted entity.
     */
    SharesDTO save(SharesDTO sharesDTO);

    /**
     * Get all the shares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SharesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" shares.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SharesDTO> findOne(Long id);

    /**
     * Delete the "id" shares.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
