package et.com.hahu.web.rest;

import et.com.hahu.service.PreferenceService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.PreferenceDTO;
import et.com.hahu.service.dto.PreferenceCriteria;
import et.com.hahu.service.PreferenceQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.Preference}.
 */
@RestController
@RequestMapping("/api")
public class PreferenceResource {

    private final Logger log = LoggerFactory.getLogger(PreferenceResource.class);

    private static final String ENTITY_NAME = "preference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreferenceService preferenceService;

    private final PreferenceQueryService preferenceQueryService;

    public PreferenceResource(PreferenceService preferenceService, PreferenceQueryService preferenceQueryService) {
        this.preferenceService = preferenceService;
        this.preferenceQueryService = preferenceQueryService;
    }

    /**
     * {@code POST  /preferences} : Create a new preference.
     *
     * @param preferenceDTO the preferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preferenceDTO, or with status {@code 400 (Bad Request)} if the preference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preferences")
    public ResponseEntity<PreferenceDTO> createPreference(@RequestBody PreferenceDTO preferenceDTO) throws URISyntaxException {
        log.debug("REST request to save Preference : {}", preferenceDTO);
        if (preferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new preference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(preferenceDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        PreferenceDTO result = preferenceService.save(preferenceDTO);
        return ResponseEntity.created(new URI("/api/preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /preferences} : Updates an existing preference.
     *
     * @param preferenceDTO the preferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preferenceDTO,
     * or with status {@code 400 (Bad Request)} if the preferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preferences")
    public ResponseEntity<PreferenceDTO> updatePreference(@RequestBody PreferenceDTO preferenceDTO) throws URISyntaxException {
        log.debug("REST request to update Preference : {}", preferenceDTO);
        if (preferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreferenceDTO result = preferenceService.save(preferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /preferences} : get all the preferences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preferences in body.
     */
    @GetMapping("/preferences")
    public ResponseEntity<List<PreferenceDTO>> getAllPreferences(PreferenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Preferences by criteria: {}", criteria);
        Page<PreferenceDTO> page = preferenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /preferences/count} : count all the preferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/preferences/count")
    public ResponseEntity<Long> countPreferences(PreferenceCriteria criteria) {
        log.debug("REST request to count Preferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(preferenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /preferences/:id} : get the "id" preference.
     *
     * @param id the id of the preferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preferences/{id}")
    public ResponseEntity<PreferenceDTO> getPreference(@PathVariable Long id) {
        log.debug("REST request to get Preference : {}", id);
        Optional<PreferenceDTO> preferenceDTO = preferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preferenceDTO);
    }

    /**
     * {@code DELETE  /preferences/:id} : delete the "id" preference.
     *
     * @param id the id of the preferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preferences/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long id) {
        log.debug("REST request to delete Preference : {}", id);

        preferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
