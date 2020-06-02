package et.com.hahu.service.impl;

import et.com.hahu.service.FamilyService;
import et.com.hahu.domain.Family;
import et.com.hahu.repository.FamilyRepository;
import et.com.hahu.repository.UserRepository;
import et.com.hahu.service.dto.FamilyDTO;
import et.com.hahu.service.mapper.FamilyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Family}.
 */
@Service
@Transactional
public class FamilyServiceImpl implements FamilyService {

    private final Logger log = LoggerFactory.getLogger(FamilyServiceImpl.class);

    private final FamilyRepository familyRepository;

    private final FamilyMapper familyMapper;

    private final UserRepository userRepository;

    public FamilyServiceImpl(FamilyRepository familyRepository, FamilyMapper familyMapper, UserRepository userRepository) {
        this.familyRepository = familyRepository;
        this.familyMapper = familyMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a family.
     *
     * @param familyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FamilyDTO save(FamilyDTO familyDTO) {
        log.debug("Request to save Family : {}", familyDTO);
        Family family = familyMapper.toEntity(familyDTO);
        Long userId = familyDTO.getUserId();
        userRepository.findById(userId).ifPresent(family::user);
        family = familyRepository.save(family);
        return familyMapper.toDto(family);
    }

    /**
     * Get all the families.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FamilyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Families");
        return familyRepository.findAll(pageable)
            .map(familyMapper::toDto);
    }


    /**
     * Get all the families with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<FamilyDTO> findAllWithEagerRelationships(Pageable pageable) {
        return familyRepository.findAllWithEagerRelationships(pageable).map(familyMapper::toDto);
    }

    /**
     * Get one family by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FamilyDTO> findOne(Long id) {
        log.debug("Request to get Family : {}", id);
        return familyRepository.findOneWithEagerRelationships(id)
            .map(familyMapper::toDto);
    }

    /**
     * Delete the family by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Family : {}", id);

        familyRepository.deleteById(id);
    }
}
