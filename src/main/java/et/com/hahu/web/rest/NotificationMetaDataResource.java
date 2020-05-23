package et.com.hahu.web.rest;

import et.com.hahu.service.NotificationMetaDataService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.NotificationMetaDataDTO;
import et.com.hahu.service.dto.NotificationMetaDataCriteria;
import et.com.hahu.service.NotificationMetaDataQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.NotificationMetaData}.
 */
@RestController
@RequestMapping("/api")
public class NotificationMetaDataResource {

    private final Logger log = LoggerFactory.getLogger(NotificationMetaDataResource.class);

    private static final String ENTITY_NAME = "notificationMetaData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationMetaDataService notificationMetaDataService;

    private final NotificationMetaDataQueryService notificationMetaDataQueryService;

    public NotificationMetaDataResource(NotificationMetaDataService notificationMetaDataService, NotificationMetaDataQueryService notificationMetaDataQueryService) {
        this.notificationMetaDataService = notificationMetaDataService;
        this.notificationMetaDataQueryService = notificationMetaDataQueryService;
    }

    /**
     * {@code POST  /notification-meta-data} : Create a new notificationMetaData.
     *
     * @param notificationMetaDataDTO the notificationMetaDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationMetaDataDTO, or with status {@code 400 (Bad Request)} if the notificationMetaData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-meta-data")
    public ResponseEntity<NotificationMetaDataDTO> createNotificationMetaData(@RequestBody NotificationMetaDataDTO notificationMetaDataDTO) throws URISyntaxException {
        log.debug("REST request to save NotificationMetaData : {}", notificationMetaDataDTO);
        if (notificationMetaDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new notificationMetaData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationMetaDataDTO result = notificationMetaDataService.save(notificationMetaDataDTO);
        return ResponseEntity.created(new URI("/api/notification-meta-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-meta-data} : Updates an existing notificationMetaData.
     *
     * @param notificationMetaDataDTO the notificationMetaDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationMetaDataDTO,
     * or with status {@code 400 (Bad Request)} if the notificationMetaDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationMetaDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-meta-data")
    public ResponseEntity<NotificationMetaDataDTO> updateNotificationMetaData(@RequestBody NotificationMetaDataDTO notificationMetaDataDTO) throws URISyntaxException {
        log.debug("REST request to update NotificationMetaData : {}", notificationMetaDataDTO);
        if (notificationMetaDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotificationMetaDataDTO result = notificationMetaDataService.save(notificationMetaDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationMetaDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notification-meta-data} : get all the notificationMetaData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationMetaData in body.
     */
    @GetMapping("/notification-meta-data")
    public ResponseEntity<List<NotificationMetaDataDTO>> getAllNotificationMetaData(NotificationMetaDataCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NotificationMetaData by criteria: {}", criteria);
        Page<NotificationMetaDataDTO> page = notificationMetaDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notification-meta-data/count} : count all the notificationMetaData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notification-meta-data/count")
    public ResponseEntity<Long> countNotificationMetaData(NotificationMetaDataCriteria criteria) {
        log.debug("REST request to count NotificationMetaData by criteria: {}", criteria);
        return ResponseEntity.ok().body(notificationMetaDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notification-meta-data/:id} : get the "id" notificationMetaData.
     *
     * @param id the id of the notificationMetaDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationMetaDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-meta-data/{id}")
    public ResponseEntity<NotificationMetaDataDTO> getNotificationMetaData(@PathVariable Long id) {
        log.debug("REST request to get NotificationMetaData : {}", id);
        Optional<NotificationMetaDataDTO> notificationMetaDataDTO = notificationMetaDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationMetaDataDTO);
    }

    /**
     * {@code DELETE  /notification-meta-data/:id} : delete the "id" notificationMetaData.
     *
     * @param id the id of the notificationMetaDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-meta-data/{id}")
    public ResponseEntity<Void> deleteNotificationMetaData(@PathVariable Long id) {
        log.debug("REST request to delete NotificationMetaData : {}", id);

        notificationMetaDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
