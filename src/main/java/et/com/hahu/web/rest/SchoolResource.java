package et.com.hahu.web.rest;

import et.com.hahu.service.SchoolService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.SchoolDTO;
import et.com.hahu.service.dto.SchoolCriteria;
import et.com.hahu.service.SchoolQueryService;

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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link et.com.hahu.domain.School}.
 */
@RestController
@RequestMapping("/api")
public class SchoolResource {

    private final Logger log = LoggerFactory.getLogger(SchoolResource.class);

    private static final String ENTITY_NAME = "school";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolService schoolService;

    private final SchoolQueryService schoolQueryService;

    public SchoolResource(SchoolService schoolService, SchoolQueryService schoolQueryService) {
        this.schoolService = schoolService;
        this.schoolQueryService = schoolQueryService;
    }

    /**
     * {@code POST  /schools} : Create a new school.
     *
     * @param schoolDTO the schoolDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schoolDTO, or with status {@code 400 (Bad Request)} if the school has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schools")
    public ResponseEntity<SchoolDTO> createSchool(@Valid @RequestBody SchoolDTO schoolDTO) throws URISyntaxException {
        log.debug("REST request to save School : {}", schoolDTO);
        if (schoolDTO.getId() != null) {
            throw new BadRequestAlertException("A new school cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolDTO result = schoolService.save(schoolDTO);
        return ResponseEntity.created(new URI("/api/schools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schools} : Updates an existing school.
     *
     * @param schoolDTO the schoolDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schoolDTO,
     * or with status {@code 400 (Bad Request)} if the schoolDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schoolDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schools")
    public ResponseEntity<SchoolDTO> updateSchool(@Valid @RequestBody SchoolDTO schoolDTO) throws URISyntaxException {
        log.debug("REST request to update School : {}", schoolDTO);
        if (schoolDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchoolDTO result = schoolService.save(schoolDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schools} : get all the schools.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schools in body.
     */
    @GetMapping("/schools")
    public ResponseEntity<List<SchoolDTO>> getAllSchools(SchoolCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Schools by criteria: {}", criteria);
        Page<SchoolDTO> page = schoolQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schools/count} : count all the schools.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/schools/count")
    public ResponseEntity<Long> countSchools(SchoolCriteria criteria) {
        log.debug("REST request to count Schools by criteria: {}", criteria);
        return ResponseEntity.ok().body(schoolQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /schools/:id} : get the "id" school.
     *
     * @param id the id of the schoolDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schoolDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schools/{id}")
    public ResponseEntity<SchoolDTO> getSchool(@PathVariable Long id) {
        log.debug("REST request to get School : {}", id);
        Optional<SchoolDTO> schoolDTO = schoolService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schoolDTO);
    }

    /**
     * {@code DELETE  /schools/:id} : delete the "id" school.
     *
     * @param id the id of the schoolDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schools/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        log.debug("REST request to delete School : {}", id);
        schoolService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
