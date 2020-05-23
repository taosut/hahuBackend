package et.com.hahu.service.impl;

import et.com.hahu.service.ScheduleTypeService;
import et.com.hahu.domain.ScheduleType;
import et.com.hahu.repository.ScheduleTypeRepository;
import et.com.hahu.service.dto.ScheduleTypeDTO;
import et.com.hahu.service.mapper.ScheduleTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ScheduleType}.
 */
@Service
@Transactional
public class ScheduleTypeServiceImpl implements ScheduleTypeService {

    private final Logger log = LoggerFactory.getLogger(ScheduleTypeServiceImpl.class);

    private final ScheduleTypeRepository scheduleTypeRepository;

    private final ScheduleTypeMapper scheduleTypeMapper;

    public ScheduleTypeServiceImpl(ScheduleTypeRepository scheduleTypeRepository, ScheduleTypeMapper scheduleTypeMapper) {
        this.scheduleTypeRepository = scheduleTypeRepository;
        this.scheduleTypeMapper = scheduleTypeMapper;
    }

    /**
     * Save a scheduleType.
     *
     * @param scheduleTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ScheduleTypeDTO save(ScheduleTypeDTO scheduleTypeDTO) {
        log.debug("Request to save ScheduleType : {}", scheduleTypeDTO);
        ScheduleType scheduleType = scheduleTypeMapper.toEntity(scheduleTypeDTO);
        scheduleType = scheduleTypeRepository.save(scheduleType);
        return scheduleTypeMapper.toDto(scheduleType);
    }

    /**
     * Get all the scheduleTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScheduleTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScheduleTypes");
        return scheduleTypeRepository.findAll(pageable)
            .map(scheduleTypeMapper::toDto);
    }

    /**
     * Get one scheduleType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleTypeDTO> findOne(Long id) {
        log.debug("Request to get ScheduleType : {}", id);
        return scheduleTypeRepository.findById(id)
            .map(scheduleTypeMapper::toDto);
    }

    /**
     * Delete the scheduleType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScheduleType : {}", id);
        scheduleTypeRepository.deleteById(id);
    }
}
