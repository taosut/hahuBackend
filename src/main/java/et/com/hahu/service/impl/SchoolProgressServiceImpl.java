package et.com.hahu.service.impl;

import et.com.hahu.service.SchoolProgressService;
import et.com.hahu.domain.SchoolProgress;
import et.com.hahu.repository.SchoolProgressRepository;
import et.com.hahu.service.dto.SchoolProgressDTO;
import et.com.hahu.service.mapper.SchoolProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SchoolProgress}.
 */
@Service
@Transactional
public class SchoolProgressServiceImpl implements SchoolProgressService {

    private final Logger log = LoggerFactory.getLogger(SchoolProgressServiceImpl.class);

    private final SchoolProgressRepository schoolProgressRepository;

    private final SchoolProgressMapper schoolProgressMapper;

    public SchoolProgressServiceImpl(SchoolProgressRepository schoolProgressRepository, SchoolProgressMapper schoolProgressMapper) {
        this.schoolProgressRepository = schoolProgressRepository;
        this.schoolProgressMapper = schoolProgressMapper;
    }

    /**
     * Save a schoolProgress.
     *
     * @param schoolProgressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SchoolProgressDTO save(SchoolProgressDTO schoolProgressDTO) {
        log.debug("Request to save SchoolProgress : {}", schoolProgressDTO);
        SchoolProgress schoolProgress = schoolProgressMapper.toEntity(schoolProgressDTO);
        schoolProgress = schoolProgressRepository.save(schoolProgress);
        return schoolProgressMapper.toDto(schoolProgress);
    }

    /**
     * Get all the schoolProgresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchoolProgressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchoolProgresses");
        return schoolProgressRepository.findAll(pageable)
            .map(schoolProgressMapper::toDto);
    }


    /**
     * Get one schoolProgress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolProgressDTO> findOne(Long id) {
        log.debug("Request to get SchoolProgress : {}", id);
        return schoolProgressRepository.findById(id)
            .map(schoolProgressMapper::toDto);
    }

    /**
     * Delete the schoolProgress by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchoolProgress : {}", id);

        schoolProgressRepository.deleteById(id);
    }
}
