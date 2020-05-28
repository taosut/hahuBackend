package et.com.hahu.service.impl;

import et.com.hahu.service.ViewsService;
import et.com.hahu.domain.Views;
import et.com.hahu.repository.ViewsRepository;
import et.com.hahu.service.dto.ViewsDTO;
import et.com.hahu.service.mapper.ViewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Views}.
 */
@Service
@Transactional
public class ViewsServiceImpl implements ViewsService {

    private final Logger log = LoggerFactory.getLogger(ViewsServiceImpl.class);

    private final ViewsRepository viewsRepository;

    private final ViewsMapper viewsMapper;

    public ViewsServiceImpl(ViewsRepository viewsRepository, ViewsMapper viewsMapper) {
        this.viewsRepository = viewsRepository;
        this.viewsMapper = viewsMapper;
    }

    /**
     * Save a views.
     *
     * @param viewsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ViewsDTO save(ViewsDTO viewsDTO) {
        log.debug("Request to save Views : {}", viewsDTO);
        Views views = viewsMapper.toEntity(viewsDTO);
        views = viewsRepository.save(views);
        return viewsMapper.toDto(views);
    }

    /**
     * Get all the views.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ViewsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Views");
        return viewsRepository.findAll(pageable)
            .map(viewsMapper::toDto);
    }


    /**
     * Get one views by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ViewsDTO> findOne(Long id) {
        log.debug("Request to get Views : {}", id);
        return viewsRepository.findById(id)
            .map(viewsMapper::toDto);
    }

    /**
     * Delete the views by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Views : {}", id);

        viewsRepository.deleteById(id);
    }
}
