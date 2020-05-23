package et.com.hahu.web.rest;

import et.com.hahu.service.ScheduleService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.ScheduleDTO;
import et.com.hahu.service.dto.ScheduleCriteria;
import et.com.hahu.service.ScheduleQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.Schedule}.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private static final String ENTITY_NAME = "schedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScheduleService scheduleService;

    private final ScheduleQueryService scheduleQueryService;

    public ScheduleResource(ScheduleService scheduleService, ScheduleQueryService scheduleQueryService) {
        this.scheduleService = scheduleService;
        this.scheduleQueryService = scheduleQueryService;
    }

    /**
     * {@code POST  /schedules} : Create a new schedule.
     *
     * @param scheduleDTO the scheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scheduleDTO, or with status {@code 400 (Bad Request)} if the schedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) throws URISyntaxException {
        log.debug("REST request to save Schedule : {}", scheduleDTO);
        if (scheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new schedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScheduleDTO result = scheduleService.save(scheduleDTO);
        return ResponseEntity.created(new URI("/api/schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schedules} : Updates an existing schedule.
     *
     * @param scheduleDTO the scheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scheduleDTO,
     * or with status {@code 400 (Bad Request)} if the scheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schedules")
    public ResponseEntity<ScheduleDTO> updateSchedule(@RequestBody ScheduleDTO scheduleDTO) throws URISyntaxException {
        log.debug("REST request to update Schedule : {}", scheduleDTO);
        if (scheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScheduleDTO result = scheduleService.save(scheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schedules} : get all the schedules.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedules in body.
     */
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules(ScheduleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Schedules by criteria: {}", criteria);
        Page<ScheduleDTO> page = scheduleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schedules/count} : count all the schedules.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/schedules/count")
    public ResponseEntity<Long> countSchedules(ScheduleCriteria criteria) {
        log.debug("REST request to count Schedules by criteria: {}", criteria);
        return ResponseEntity.ok().body(scheduleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /schedules/:id} : get the "id" schedule.
     *
     * @param id the id of the scheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schedules/{id}")
    public ResponseEntity<ScheduleDTO> getSchedule(@PathVariable Long id) {
        log.debug("REST request to get Schedule : {}", id);
        Optional<ScheduleDTO> scheduleDTO = scheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scheduleDTO);
    }

    /**
     * {@code DELETE  /schedules/:id} : delete the "id" schedule.
     *
     * @param id the id of the scheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        log.debug("REST request to delete Schedule : {}", id);

        scheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
