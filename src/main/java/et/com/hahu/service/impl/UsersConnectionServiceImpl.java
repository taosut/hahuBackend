package et.com.hahu.service.impl;

import et.com.hahu.service.UsersConnectionService;
import et.com.hahu.domain.UsersConnection;
import et.com.hahu.repository.UsersConnectionRepository;
import et.com.hahu.service.dto.UsersConnectionDTO;
import et.com.hahu.service.mapper.UsersConnectionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UsersConnection}.
 */
@Service
@Transactional
public class UsersConnectionServiceImpl implements UsersConnectionService {

    private final Logger log = LoggerFactory.getLogger(UsersConnectionServiceImpl.class);

    private final UsersConnectionRepository usersConnectionRepository;

    private final UsersConnectionMapper usersConnectionMapper;

    public UsersConnectionServiceImpl(UsersConnectionRepository usersConnectionRepository, UsersConnectionMapper usersConnectionMapper) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.usersConnectionMapper = usersConnectionMapper;
    }

    /**
     * Save a usersConnection.
     *
     * @param usersConnectionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UsersConnectionDTO save(UsersConnectionDTO usersConnectionDTO) {
        log.debug("Request to save UsersConnection : {}", usersConnectionDTO);
        UsersConnection usersConnection = usersConnectionMapper.toEntity(usersConnectionDTO);
        usersConnection = usersConnectionRepository.save(usersConnection);
        return usersConnectionMapper.toDto(usersConnection);
    }

    /**
     * Get all the usersConnections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UsersConnectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UsersConnections");
        return usersConnectionRepository.findAll(pageable)
            .map(usersConnectionMapper::toDto);
    }

    /**
     * Get one usersConnection by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UsersConnectionDTO> findOne(Long id) {
        log.debug("Request to get UsersConnection : {}", id);
        return usersConnectionRepository.findById(id)
            .map(usersConnectionMapper::toDto);
    }

    /**
     * Delete the usersConnection by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UsersConnection : {}", id);
        usersConnectionRepository.deleteById(id);
    }
}
