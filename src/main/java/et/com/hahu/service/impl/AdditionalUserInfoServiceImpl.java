package et.com.hahu.service.impl;

import et.com.hahu.service.AdditionalUserInfoService;
import et.com.hahu.domain.AdditionalUserInfo;
import et.com.hahu.repository.AdditionalUserInfoRepository;
import et.com.hahu.repository.UserRepository;
import et.com.hahu.service.dto.AdditionalUserInfoDTO;
import et.com.hahu.service.mapper.AdditionalUserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AdditionalUserInfo}.
 */
@Service
@Transactional
public class AdditionalUserInfoServiceImpl implements AdditionalUserInfoService {

    private final Logger log = LoggerFactory.getLogger(AdditionalUserInfoServiceImpl.class);

    private final AdditionalUserInfoRepository additionalUserInfoRepository;

    private final AdditionalUserInfoMapper additionalUserInfoMapper;

    private final UserRepository userRepository;

    public AdditionalUserInfoServiceImpl(AdditionalUserInfoRepository additionalUserInfoRepository, AdditionalUserInfoMapper additionalUserInfoMapper, UserRepository userRepository) {
        this.additionalUserInfoRepository = additionalUserInfoRepository;
        this.additionalUserInfoMapper = additionalUserInfoMapper;
        this.userRepository = userRepository;
    }

    /**
     * Save a additionalUserInfo.
     *
     * @param additionalUserInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AdditionalUserInfoDTO save(AdditionalUserInfoDTO additionalUserInfoDTO) {
        log.debug("Request to save AdditionalUserInfo : {}", additionalUserInfoDTO);
        AdditionalUserInfo additionalUserInfo = additionalUserInfoMapper.toEntity(additionalUserInfoDTO);
        long userId = additionalUserInfoDTO.getUserId();
        userRepository.findById(userId).ifPresent(additionalUserInfo::user);
        additionalUserInfo = additionalUserInfoRepository.save(additionalUserInfo);
        return additionalUserInfoMapper.toDto(additionalUserInfo);
    }

    /**
     * Get all the additionalUserInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdditionalUserInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AdditionalUserInfos");
        return additionalUserInfoRepository.findAll(pageable)
            .map(additionalUserInfoMapper::toDto);
    }

    /**
     * Get one additionalUserInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdditionalUserInfoDTO> findOne(Long id) {
        log.debug("Request to get AdditionalUserInfo : {}", id);
        return additionalUserInfoRepository.findById(id)
            .map(additionalUserInfoMapper::toDto);
    }

    /**
     * Delete the additionalUserInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AdditionalUserInfo : {}", id);
        additionalUserInfoRepository.deleteById(id);
    }
}
