package et.com.hahu.web.rest;

import et.com.hahu.service.ImageMetaDataService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.ImageMetaDataDTO;
import et.com.hahu.service.dto.ImageMetaDataCriteria;
import et.com.hahu.service.ImageMetaDataQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link et.com.hahu.domain.ImageMetaData}.
 */
@RestController
@RequestMapping("/api")
public class ImageMetaDataResource {

    private final Logger log = LoggerFactory.getLogger(ImageMetaDataResource.class);

    private static final String ENTITY_NAME = "imageMetaData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageMetaDataService imageMetaDataService;

    private final ImageMetaDataQueryService imageMetaDataQueryService;

    public ImageMetaDataResource(ImageMetaDataService imageMetaDataService, ImageMetaDataQueryService imageMetaDataQueryService) {
        this.imageMetaDataService = imageMetaDataService;
        this.imageMetaDataQueryService = imageMetaDataQueryService;
    }

    /**
     * {@code POST  /image-meta-data} : Create a new imageMetaData.
     *
     * @param imageMetaDataDTO the imageMetaDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageMetaDataDTO, or with status {@code 400 (Bad Request)} if the imageMetaData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/image-meta-data")
    public ResponseEntity<ImageMetaDataDTO> createImageMetaData(@RequestBody ImageMetaDataDTO imageMetaDataDTO) throws URISyntaxException {
        log.debug("REST request to save ImageMetaData : {}", imageMetaDataDTO);
        if (imageMetaDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new imageMetaData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageMetaDataDTO result = imageMetaDataService.save(imageMetaDataDTO);
        return ResponseEntity.created(new URI("/api/image-meta-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /image-meta-data} : Updates an existing imageMetaData.
     *
     * @param imageMetaDataDTO the imageMetaDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageMetaDataDTO,
     * or with status {@code 400 (Bad Request)} if the imageMetaDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageMetaDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/image-meta-data")
    public ResponseEntity<ImageMetaDataDTO> updateImageMetaData(@RequestBody ImageMetaDataDTO imageMetaDataDTO) throws URISyntaxException {
        log.debug("REST request to update ImageMetaData : {}", imageMetaDataDTO);
        if (imageMetaDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImageMetaDataDTO result = imageMetaDataService.save(imageMetaDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageMetaDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /image-meta-data} : get all the imageMetaData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageMetaData in body.
     */
    @GetMapping("/image-meta-data")
    public ResponseEntity<List<ImageMetaDataDTO>> getAllImageMetaData(ImageMetaDataCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ImageMetaData by criteria: {}", criteria);
        Page<ImageMetaDataDTO> page = imageMetaDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /image-meta-data/count} : count all the imageMetaData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/image-meta-data/count")
    public ResponseEntity<Long> countImageMetaData(ImageMetaDataCriteria criteria) {
        log.debug("REST request to count ImageMetaData by criteria: {}", criteria);
        return ResponseEntity.ok().body(imageMetaDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /image-meta-data/:id} : get the "id" imageMetaData.
     *
     * @param id the id of the imageMetaDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageMetaDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/image-meta-data/{id}")
    public ResponseEntity<ImageMetaDataDTO> getImageMetaData(@PathVariable Long id) {
        log.debug("REST request to get ImageMetaData : {}", id);
        Optional<ImageMetaDataDTO> imageMetaDataDTO = imageMetaDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageMetaDataDTO);
    }

    /**
     * {@code DELETE  /image-meta-data/:id} : delete the "id" imageMetaData.
     *
     * @param id the id of the imageMetaDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/image-meta-data/{id}")
    public ResponseEntity<Void> deleteImageMetaData(@PathVariable Long id) {
        log.debug("REST request to delete ImageMetaData : {}", id);

        imageMetaDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
