package et.com.hahu.service.impl;

import et.com.hahu.service.PreferenceService;
import et.com.hahu.domain.Preference;
import et.com.hahu.repository.PreferenceRepository;
import et.com.hahu.repository.UserRepository;
import et.com.hahu.service.dto.PreferenceDTO;
import et.com.hahu.service.mapper.PreferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Preference}.
 */
@Service
@Transactional
public class PreferenceServiceImpl implements PreferenceService {

    private final Logger log = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    private final PreferenceRepository preferenceRepository;

    private final PreferenceMapper preferenceMapper;

    private final UserRepository userRepository;

    public PreferenceServiceImpl(PreferenceRepository preferenceRepository, PreferenceMapper preferenceMapper, UserRepository userRepository) {
        this.preferenceRepository = preferenceRepository;
        this.preferenceMapper = preferenceMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a preference.
     *
     * @param preferenceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PreferenceDTO save(PreferenceDTO preferenceDTO) {
        log.debug("Request to save Preference : {}", preferenceDTO);
        Preference preference = preferenceMapper.toEntity(preferenceDTO);
        long userId = preferenceDTO.getUserId();
        userRepository.findById(userId).ifPresent(preference::user);
        preference = preferenceRepository.save(preference);
        return preferenceMapper.toDto(preference);
    }

    /**
     * Get all the preferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PreferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Preferences");
        return preferenceRepository.findAll(pageable)
            .map(preferenceMapper::toDto);
    }

    /**
     * Get one preference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PreferenceDTO> findOne(Long id) {
        log.debug("Request to get Preference : {}", id);
        return preferenceRepository.findById(id)
            .map(preferenceMapper::toDto);
    }

    /**
     * Delete the preference by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Preference : {}", id);
        preferenceRepository.deleteById(id);
    }
}
