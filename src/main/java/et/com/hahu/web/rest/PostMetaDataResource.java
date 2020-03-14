package et.com.hahu.web.rest;

import et.com.hahu.service.PostMetaDataService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.PostMetaDataDTO;
import et.com.hahu.service.dto.PostMetaDataCriteria;
import et.com.hahu.service.PostMetaDataQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.PostMetaData}.
 */
@RestController
@RequestMapping("/api")
public class PostMetaDataResource {

    private final Logger log = LoggerFactory.getLogger(PostMetaDataResource.class);

    private static final String ENTITY_NAME = "postMetaData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostMetaDataService postMetaDataService;

    private final PostMetaDataQueryService postMetaDataQueryService;

    public PostMetaDataResource(PostMetaDataService postMetaDataService, PostMetaDataQueryService postMetaDataQueryService) {
        this.postMetaDataService = postMetaDataService;
        this.postMetaDataQueryService = postMetaDataQueryService;
    }

    /**
     * {@code POST  /post-meta-data} : Create a new postMetaData.
     *
     * @param postMetaDataDTO the postMetaDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postMetaDataDTO, or with status {@code 400 (Bad Request)} if the postMetaData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-meta-data")
    public ResponseEntity<PostMetaDataDTO> createPostMetaData(@RequestBody PostMetaDataDTO postMetaDataDTO) throws URISyntaxException {
        log.debug("REST request to save PostMetaData : {}", postMetaDataDTO);
        if (postMetaDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new postMetaData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostMetaDataDTO result = postMetaDataService.save(postMetaDataDTO);
        return ResponseEntity.created(new URI("/api/post-meta-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-meta-data} : Updates an existing postMetaData.
     *
     * @param postMetaDataDTO the postMetaDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postMetaDataDTO,
     * or with status {@code 400 (Bad Request)} if the postMetaDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postMetaDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-meta-data")
    public ResponseEntity<PostMetaDataDTO> updatePostMetaData(@RequestBody PostMetaDataDTO postMetaDataDTO) throws URISyntaxException {
        log.debug("REST request to update PostMetaData : {}", postMetaDataDTO);
        if (postMetaDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostMetaDataDTO result = postMetaDataService.save(postMetaDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postMetaDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-meta-data} : get all the postMetaData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postMetaData in body.
     */
    @GetMapping("/post-meta-data")
    public ResponseEntity<List<PostMetaDataDTO>> getAllPostMetaData(PostMetaDataCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PostMetaData by criteria: {}", criteria);
        Page<PostMetaDataDTO> page = postMetaDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-meta-data/count} : count all the postMetaData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/post-meta-data/count")
    public ResponseEntity<Long> countPostMetaData(PostMetaDataCriteria criteria) {
        log.debug("REST request to count PostMetaData by criteria: {}", criteria);
        return ResponseEntity.ok().body(postMetaDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /post-meta-data/:id} : get the "id" postMetaData.
     *
     * @param id the id of the postMetaDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postMetaDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-meta-data/{id}")
    public ResponseEntity<PostMetaDataDTO> getPostMetaData(@PathVariable Long id) {
        log.debug("REST request to get PostMetaData : {}", id);
        Optional<PostMetaDataDTO> postMetaDataDTO = postMetaDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postMetaDataDTO);
    }

    /**
     * {@code DELETE  /post-meta-data/:id} : delete the "id" postMetaData.
     *
     * @param id the id of the postMetaDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-meta-data/{id}")
    public ResponseEntity<Void> deletePostMetaData(@PathVariable Long id) {
        log.debug("REST request to delete PostMetaData : {}", id);
        postMetaDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
