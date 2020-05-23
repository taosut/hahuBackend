package et.com.hahu.service.impl;

import et.com.hahu.service.LikesService;
import et.com.hahu.domain.Likes;
import et.com.hahu.repository.LikesRepository;
import et.com.hahu.service.dto.LikesDTO;
import et.com.hahu.service.mapper.LikesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Likes}.
 */
@Service
@Transactional
public class LikesServiceImpl implements LikesService {

    private final Logger log = LoggerFactory.getLogger(LikesServiceImpl.class);

    private final LikesRepository likesRepository;

    private final LikesMapper likesMapper;

    public LikesServiceImpl(LikesRepository likesRepository, LikesMapper likesMapper) {
        this.likesRepository = likesRepository;
        this.likesMapper = likesMapper;
    }

    /**
     * Save a likes.
     *
     * @param likesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LikesDTO save(LikesDTO likesDTO) {
        log.debug("Request to save Likes : {}", likesDTO);
        Likes likes = likesMapper.toEntity(likesDTO);
        likes = likesRepository.save(likes);
        return likesMapper.toDto(likes);
    }

    /**
     * Get all the likes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LikesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Likes");
        return likesRepository.findAll(pageable)
            .map(likesMapper::toDto);
    }


    /**
     * Get one likes by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LikesDTO> findOne(Long id) {
        log.debug("Request to get Likes : {}", id);
        return likesRepository.findById(id)
            .map(likesMapper::toDto);
    }

    /**
     * Delete the likes by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Likes : {}", id);

        likesRepository.deleteById(id);
    }
}
