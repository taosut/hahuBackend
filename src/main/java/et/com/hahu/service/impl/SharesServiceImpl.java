package et.com.hahu.service.impl;

import et.com.hahu.service.SharesService;
import et.com.hahu.domain.Shares;
import et.com.hahu.repository.SharesRepository;
import et.com.hahu.service.dto.SharesDTO;
import et.com.hahu.service.mapper.SharesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Shares}.
 */
@Service
@Transactional
public class SharesServiceImpl implements SharesService {

    private final Logger log = LoggerFactory.getLogger(SharesServiceImpl.class);

    private final SharesRepository sharesRepository;

    private final SharesMapper sharesMapper;

    public SharesServiceImpl(SharesRepository sharesRepository, SharesMapper sharesMapper) {
        this.sharesRepository = sharesRepository;
        this.sharesMapper = sharesMapper;
    }

    /**
     * Save a shares.
     *
     * @param sharesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SharesDTO save(SharesDTO sharesDTO) {
        log.debug("Request to save Shares : {}", sharesDTO);
        Shares shares = sharesMapper.toEntity(sharesDTO);
        shares = sharesRepository.save(shares);
        return sharesMapper.toDto(shares);
    }

    /**
     * Get all the shares.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SharesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shares");
        return sharesRepository.findAll(pageable)
            .map(sharesMapper::toDto);
    }


    /**
     * Get one shares by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SharesDTO> findOne(Long id) {
        log.debug("Request to get Shares : {}", id);
        return sharesRepository.findById(id)
            .map(sharesMapper::toDto);
    }

    /**
     * Delete the shares by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shares : {}", id);

        sharesRepository.deleteById(id);
    }
}
