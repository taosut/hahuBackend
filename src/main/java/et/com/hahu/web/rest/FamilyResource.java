package et.com.hahu.web.rest;

import et.com.hahu.service.FamilyService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.FamilyDTO;
import et.com.hahu.service.dto.FamilyCriteria;
import et.com.hahu.service.FamilyQueryService;

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
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link et.com.hahu.domain.Family}.
 */
@RestController
@RequestMapping("/api")
public class FamilyResource {

    private final Logger log = LoggerFactory.getLogger(FamilyResource.class);

    private static final String ENTITY_NAME = "family";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FamilyService familyService;

    private final FamilyQueryService familyQueryService;

    public FamilyResource(FamilyService familyService, FamilyQueryService familyQueryService) {
        this.familyService = familyService;
        this.familyQueryService = familyQueryService;
    }

    /**
     * {@code POST  /families} : Create a new family.
     *
     * @param familyDTO the familyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new familyDTO, or with status {@code 400 (Bad Request)} if the family has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/families")
    public ResponseEntity<FamilyDTO> createFamily(@RequestBody FamilyDTO familyDTO) throws URISyntaxException {
        log.debug("REST request to save Family : {}", familyDTO);
        if (familyDTO.getId() != null) {
            throw new BadRequestAlertException("A new family cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(familyDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        FamilyDTO result = familyService.save(familyDTO);
        return ResponseEntity.created(new URI("/api/families/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /families} : Updates an existing family.
     *
     * @param familyDTO the familyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated familyDTO,
     * or with status {@code 400 (Bad Request)} if the familyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the familyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/families")
    public ResponseEntity<FamilyDTO> updateFamily(@RequestBody FamilyDTO familyDTO) throws URISyntaxException {
        log.debug("REST request to update Family : {}", familyDTO);
        if (familyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FamilyDTO result = familyService.save(familyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, familyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /families} : get all the families.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of families in body.
     */
    @GetMapping("/families")
    public ResponseEntity<List<FamilyDTO>> getAllFamilies(FamilyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Families by criteria: {}", criteria);
        Page<FamilyDTO> page = familyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /families/count} : count all the families.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/families/count")
    public ResponseEntity<Long> countFamilies(FamilyCriteria criteria) {
        log.debug("REST request to count Families by criteria: {}", criteria);
        return ResponseEntity.ok().body(familyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /families/:id} : get the "id" family.
     *
     * @param id the id of the familyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the familyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/families/{id}")
    public ResponseEntity<FamilyDTO> getFamily(@PathVariable Long id) {
        log.debug("REST request to get Family : {}", id);
        Optional<FamilyDTO> familyDTO = familyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(familyDTO);
    }

    /**
     * {@code DELETE  /families/:id} : delete the "id" family.
     *
     * @param id the id of the familyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/families/{id}")
    public ResponseEntity<Void> deleteFamily(@PathVariable Long id) {
        log.debug("REST request to delete Family : {}", id);

        familyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
