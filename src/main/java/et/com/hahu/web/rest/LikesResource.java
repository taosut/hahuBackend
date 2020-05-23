package et.com.hahu.web.rest;

import et.com.hahu.service.LikesService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.LikesDTO;
import et.com.hahu.service.dto.LikesCriteria;
import et.com.hahu.service.LikesQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.Likes}.
 */
@RestController
@RequestMapping("/api")
public class LikesResource {

    private final Logger log = LoggerFactory.getLogger(LikesResource.class);

    private static final String ENTITY_NAME = "likes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikesService likesService;

    private final LikesQueryService likesQueryService;

    public LikesResource(LikesService likesService, LikesQueryService likesQueryService) {
        this.likesService = likesService;
        this.likesQueryService = likesQueryService;
    }

    /**
     * {@code POST  /likes} : Create a new likes.
     *
     * @param likesDTO the likesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likesDTO, or with status {@code 400 (Bad Request)} if the likes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/likes")
    public ResponseEntity<LikesDTO> createLikes(@RequestBody LikesDTO likesDTO) throws URISyntaxException {
        log.debug("REST request to save Likes : {}", likesDTO);
        if (likesDTO.getId() != null) {
            throw new BadRequestAlertException("A new likes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikesDTO result = likesService.save(likesDTO);
        return ResponseEntity.created(new URI("/api/likes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /likes} : Updates an existing likes.
     *
     * @param likesDTO the likesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likesDTO,
     * or with status {@code 400 (Bad Request)} if the likesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/likes")
    public ResponseEntity<LikesDTO> updateLikes(@RequestBody LikesDTO likesDTO) throws URISyntaxException {
        log.debug("REST request to update Likes : {}", likesDTO);
        if (likesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LikesDTO result = likesService.save(likesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, likesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /likes} : get all the likes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likes in body.
     */
    @GetMapping("/likes")
    public ResponseEntity<List<LikesDTO>> getAllLikes(LikesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Likes by criteria: {}", criteria);
        Page<LikesDTO> page = likesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /likes/count} : count all the likes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/likes/count")
    public ResponseEntity<Long> countLikes(LikesCriteria criteria) {
        log.debug("REST request to count Likes by criteria: {}", criteria);
        return ResponseEntity.ok().body(likesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /likes/:id} : get the "id" likes.
     *
     * @param id the id of the likesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/likes/{id}")
    public ResponseEntity<LikesDTO> getLikes(@PathVariable Long id) {
        log.debug("REST request to get Likes : {}", id);
        Optional<LikesDTO> likesDTO = likesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likesDTO);
    }

    /**
     * {@code DELETE  /likes/:id} : delete the "id" likes.
     *
     * @param id the id of the likesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/likes/{id}")
    public ResponseEntity<Void> deleteLikes(@PathVariable Long id) {
        log.debug("REST request to delete Likes : {}", id);

        likesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
