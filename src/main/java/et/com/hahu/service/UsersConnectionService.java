package et.com.hahu.service;

import et.com.hahu.service.dto.UsersConnectionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.UsersConnection}.
 */
public interface UsersConnectionService {

    /**
     * Save a usersConnection.
     *
     * @param usersConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    UsersConnectionDTO save(UsersConnectionDTO usersConnectionDTO);

    /**
     * Get all the usersConnections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsersConnectionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" usersConnection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsersConnectionDTO> findOne(Long id);

    /**
     * Delete the "id" usersConnection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
