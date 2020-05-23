package et.com.hahu.service;

import et.com.hahu.service.dto.ScheduleTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link et.com.hahu.domain.ScheduleType}.
 */
public interface ScheduleTypeService {

    /**
     * Save a scheduleType.
     *
     * @param scheduleTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ScheduleTypeDTO save(ScheduleTypeDTO scheduleTypeDTO);

    /**
     * Get all the scheduleTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScheduleTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" scheduleType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScheduleTypeDTO> findOne(Long id);

    /**
     * Delete the "id" scheduleType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
