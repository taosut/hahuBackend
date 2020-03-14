package et.com.hahu.web.rest;

import et.com.hahu.service.ScheduleTypeService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.ScheduleTypeDTO;
import et.com.hahu.service.dto.ScheduleTypeCriteria;
import et.com.hahu.service.ScheduleTypeQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.ScheduleType}.
 */
@RestController
@RequestMapping("/api")
public class ScheduleTypeResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleTypeResource.class);

    private static final String ENTITY_NAME = "scheduleType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleTypeService scheduleTypeService;

    private final ScheduleTypeQueryService scheduleTypeQueryService;

    public ScheduleTypeResource(ScheduleTypeService scheduleTypeService, ScheduleTypeQueryService scheduleTypeQueryService) {
        this.scheduleTypeService = scheduleTypeService;
        this.scheduleTypeQueryService = scheduleTypeQueryService;
    }

    /**
     * {@code POST  /schedule-types} : Create a new scheduleType.
     *
     * @param scheduleTypeDTO the scheduleTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduleTypeDTO, or with status {@code 400 (Bad Request)} if the scheduleType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedule-types")
    public ResponseEntity<ScheduleTypeDTO> createScheduleType(@RequestBody ScheduleTypeDTO scheduleTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ScheduleType : {}", scheduleTypeDTO);
        if (scheduleTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new scheduleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleTypeDTO result = scheduleTypeService.save(scheduleTypeDTO);
        return ResponseEntity.created(new URI("/api/schedule-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schedule-types} : Updates an existing scheduleType.
     *
     * @param scheduleTypeDTO the scheduleTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleTypeDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduleTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedule-types")
    public ResponseEntity<ScheduleTypeDTO> updateScheduleType(@RequestBody ScheduleTypeDTO scheduleTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ScheduleType : {}", scheduleTypeDTO);
        if (scheduleTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleTypeDTO result = scheduleTypeService.save(scheduleTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduleTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schedule-types} : get all the scheduleTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scheduleTypes in body.
     */
    @GetMapping("/schedule-types")
    public ResponseEntity<List<ScheduleTypeDTO>> getAllScheduleTypes(ScheduleTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ScheduleTypes by criteria: {}", criteria);
        Page<ScheduleTypeDTO> page = scheduleTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schedule-types/count} : count all the scheduleTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/schedule-types/count")
    public ResponseEntity<Long> countScheduleTypes(ScheduleTypeCriteria criteria) {
        log.debug("REST request to count ScheduleTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(scheduleTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /schedule-types/:id} : get the "id" scheduleType.
     *
     * @param id the id of the scheduleTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduleTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedule-types/{id}")
    public ResponseEntity<ScheduleTypeDTO> getScheduleType(@PathVariable Long id) {
        log.debug("REST request to get ScheduleType : {}", id);
        Optional<ScheduleTypeDTO> scheduleTypeDTO = scheduleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleTypeDTO);
    }

    /**
     * {@code DELETE  /schedule-types/:id} : delete the "id" scheduleType.
     *
     * @param id the id of the scheduleTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedule-types/{id}")
    public ResponseEntity<Void> deleteScheduleType(@PathVariable Long id) {
        log.debug("REST request to delete ScheduleType : {}", id);
        scheduleTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
