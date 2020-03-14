package et.com.hahu.service.impl;

import et.com.hahu.service.ImageMetaDataService;
import et.com.hahu.domain.ImageMetaData;
import et.com.hahu.repository.ImageMetaDataRepository;
import et.com.hahu.service.dto.ImageMetaDataDTO;
import et.com.hahu.service.mapper.ImageMetaDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ImageMetaData}.
 */
@Service
@Transactional
public class ImageMetaDataServiceImpl implements ImageMetaDataService {

    private final Logger log = LoggerFactory.getLogger(ImageMetaDataServiceImpl.class);

    private final ImageMetaDataRepository imageMetaDataRepository;

    private final ImageMetaDataMapper imageMetaDataMapper;

    public ImageMetaDataServiceImpl(ImageMetaDataRepository imageMetaDataRepository, ImageMetaDataMapper imageMetaDataMapper) {
        this.imageMetaDataRepository = imageMetaDataRepository;
        this.imageMetaDataMapper = imageMetaDataMapper;
    }

    /**
     * Save a imageMetaData.
     *
     * @param imageMetaDataDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImageMetaDataDTO save(ImageMetaDataDTO imageMetaDataDTO) {
        log.debug("Request to save ImageMetaData : {}", imageMetaDataDTO);
        ImageMetaData imageMetaData = imageMetaDataMapper.toEntity(imageMetaDataDTO);
        imageMetaData = imageMetaDataRepository.save(imageMetaData);
        return imageMetaDataMapper.toDto(imageMetaData);
    }

    /**
     * Get all the imageMetaData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ImageMetaDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ImageMetaData");
        return imageMetaDataRepository.findAll(pageable)
            .map(imageMetaDataMapper::toDto);
    }

    /**
     * Get one imageMetaData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImageMetaDataDTO> findOne(Long id) {
        log.debug("Request to get ImageMetaData : {}", id);
        return imageMetaDataRepository.findById(id)
            .map(imageMetaDataMapper::toDto);
    }

    /**
     * Delete the imageMetaData by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImageMetaData : {}", id);
        imageMetaDataRepository.deleteById(id);
    }
}
