package et.com.hahu.web.rest;

import et.com.hahu.service.SchoolProgressService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.SchoolProgressDTO;
import et.com.hahu.service.dto.SchoolProgressCriteria;
import et.com.hahu.service.SchoolProgressQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.SchoolProgress}.
 */
@RestController
@RequestMapping("/api")
public class SchoolProgressResource {

    private final Logger log = LoggerFactory.getLogger(SchoolProgressResource.class);

    private static final String ENTITY_NAME = "schoolProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolProgressService schoolProgressService;

    private final SchoolProgressQueryService schoolProgressQueryService;

    public SchoolProgressResource(SchoolProgressService schoolProgressService, SchoolProgressQueryService schoolProgressQueryService) {
        this.schoolProgressService = schoolProgressService;
        this.schoolProgressQueryService = schoolProgressQueryService;
    }

    /**
     * {@code POST  /school-progresses} : Create a new schoolProgress.
     *
     * @param schoolProgressDTO the schoolProgressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schoolProgressDTO, or with status {@code 400 (Bad Request)} if the schoolProgress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/school-progresses")
    public ResponseEntity<SchoolProgressDTO> createSchoolProgress(@RequestBody SchoolProgressDTO schoolProgressDTO) throws URISyntaxException {
        log.debug("REST request to save SchoolProgress : {}", schoolProgressDTO);
        if (schoolProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolProgressDTO result = schoolProgressService.save(schoolProgressDTO);
        return ResponseEntity.created(new URI("/api/school-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /school-progresses} : Updates an existing schoolProgress.
     *
     * @param schoolProgressDTO the schoolProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schoolProgressDTO,
     * or with status {@code 400 (Bad Request)} if the schoolProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schoolProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/school-progresses")
    public ResponseEntity<SchoolProgressDTO> updateSchoolProgress(@RequestBody SchoolProgressDTO schoolProgressDTO) throws URISyntaxException {
        log.debug("REST request to update SchoolProgress : {}", schoolProgressDTO);
        if (schoolProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchoolProgressDTO result = schoolProgressService.save(schoolProgressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /school-progresses} : get all the schoolProgresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schoolProgresses in body.
     */
    @GetMapping("/school-progresses")
    public ResponseEntity<List<SchoolProgressDTO>> getAllSchoolProgresses(SchoolProgressCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SchoolProgresses by criteria: {}", criteria);
        Page<SchoolProgressDTO> page = schoolProgressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /school-progresses/count} : count all the schoolProgresses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/school-progresses/count")
    public ResponseEntity<Long> countSchoolProgresses(SchoolProgressCriteria criteria) {
        log.debug("REST request to count SchoolProgresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolProgressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /school-progresses/:id} : get the "id" schoolProgress.
     *
     * @param id the id of the schoolProgressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schoolProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/school-progresses/{id}")
    public ResponseEntity<SchoolProgressDTO> getSchoolProgress(@PathVariable Long id) {
        log.debug("REST request to get SchoolProgress : {}", id);
        Optional<SchoolProgressDTO> schoolProgressDTO = schoolProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schoolProgressDTO);
    }

    /**
     * {@code DELETE  /school-progresses/:id} : delete the "id" schoolProgress.
     *
     * @param id the id of the schoolProgressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/school-progresses/{id}")
    public ResponseEntity<Void> deleteSchoolProgress(@PathVariable Long id) {
        log.debug("REST request to delete SchoolProgress : {}", id);

        schoolProgressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
