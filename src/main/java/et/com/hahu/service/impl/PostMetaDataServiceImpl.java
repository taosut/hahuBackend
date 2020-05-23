package et.com.hahu.service.impl;

import et.com.hahu.service.PostMetaDataService;
import et.com.hahu.domain.PostMetaData;
import et.com.hahu.repository.PostMetaDataRepository;
import et.com.hahu.service.dto.PostMetaDataDTO;
import et.com.hahu.service.mapper.PostMetaDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostMetaData}.
 */
@Service
@Transactional
public class PostMetaDataServiceImpl implements PostMetaDataService {

    private final Logger log = LoggerFactory.getLogger(PostMetaDataServiceImpl.class);

    private final PostMetaDataRepository postMetaDataRepository;

    private final PostMetaDataMapper postMetaDataMapper;

    public PostMetaDataServiceImpl(PostMetaDataRepository postMetaDataRepository, PostMetaDataMapper postMetaDataMapper) {
        this.postMetaDataRepository = postMetaDataRepository;
        this.postMetaDataMapper = postMetaDataMapper;
    }

    /**
     * Save a postMetaData.
     *
     * @param postMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PostMetaDataDTO save(PostMetaDataDTO postMetaDataDTO) {
        log.debug("Request to save PostMetaData : {}", postMetaDataDTO);
        PostMetaData postMetaData = postMetaDataMapper.toEntity(postMetaDataDTO);
        postMetaData = postMetaDataRepository.save(postMetaData);
        return postMetaDataMapper.toDto(postMetaData);
    }

    /**
     * Get all the postMetaData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PostMetaDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostMetaData");
        return postMetaDataRepository.findAll(pageable)
            .map(postMetaDataMapper::toDto);
    }


    /**
     * Get one postMetaData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PostMetaDataDTO> findOne(Long id) {
        log.debug("Request to get PostMetaData : {}", id);
        return postMetaDataRepository.findById(id)
            .map(postMetaDataMapper::toDto);
    }

    /**
     * Delete the postMetaData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostMetaData : {}", id);

        postMetaDataRepository.deleteById(id);
    }
}
