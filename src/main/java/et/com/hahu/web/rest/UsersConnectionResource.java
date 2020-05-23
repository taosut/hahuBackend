package et.com.hahu.web.rest;

import et.com.hahu.service.UsersConnectionService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.UsersConnectionDTO;
import et.com.hahu.service.dto.UsersConnectionCriteria;
import et.com.hahu.service.UsersConnectionQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.UsersConnection}.
 */
@RestController
@RequestMapping("/api")
public class UsersConnectionResource {

    private final Logger log = LoggerFactory.getLogger(UsersConnectionResource.class);

    private static final String ENTITY_NAME = "usersConnection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsersConnectionService usersConnectionService;

    private final UsersConnectionQueryService usersConnectionQueryService;

    public UsersConnectionResource(UsersConnectionService usersConnectionService, UsersConnectionQueryService usersConnectionQueryService) {
        this.usersConnectionService = usersConnectionService;
        this.usersConnectionQueryService = usersConnectionQueryService;
    }

    /**
     * {@code POST  /users-connections} : Create a new usersConnection.
     *
     * @param usersConnectionDTO the usersConnectionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usersConnectionDTO, or with status {@code 400 (Bad Request)} if the usersConnection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/users-connections")
    public ResponseEntity<UsersConnectionDTO> createUsersConnection(@RequestBody UsersConnectionDTO usersConnectionDTO) throws URISyntaxException {
        log.debug("REST request to save UsersConnection : {}", usersConnectionDTO);
        if (usersConnectionDTO.getId() != null) {
            throw new BadRequestAlertException("A new usersConnection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UsersConnectionDTO result = usersConnectionService.save(usersConnectionDTO);
        return ResponseEntity.created(new URI("/api/users-connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /users-connections} : Updates an existing usersConnection.
     *
     * @param usersConnectionDTO the usersConnectionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usersConnectionDTO,
     * or with status {@code 400 (Bad Request)} if the usersConnectionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usersConnectionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/users-connections")
    public ResponseEntity<UsersConnectionDTO> updateUsersConnection(@RequestBody UsersConnectionDTO usersConnectionDTO) throws URISyntaxException {
        log.debug("REST request to update UsersConnection : {}", usersConnectionDTO);
        if (usersConnectionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UsersConnectionDTO result = usersConnectionService.save(usersConnectionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usersConnectionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /users-connections} : get all the usersConnections.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usersConnections in body.
     */
    @GetMapping("/users-connections")
    public ResponseEntity<List<UsersConnectionDTO>> getAllUsersConnections(UsersConnectionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UsersConnections by criteria: {}", criteria);
        Page<UsersConnectionDTO> page = usersConnectionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /users-connections/count} : count all the usersConnections.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/users-connections/count")
    public ResponseEntity<Long> countUsersConnections(UsersConnectionCriteria criteria) {
        log.debug("REST request to count UsersConnections by criteria: {}", criteria);
        return ResponseEntity.ok().body(usersConnectionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /users-connections/:id} : get the "id" usersConnection.
     *
     * @param id the id of the usersConnectionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usersConnectionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users-connections/{id}")
    public ResponseEntity<UsersConnectionDTO> getUsersConnection(@PathVariable Long id) {
        log.debug("REST request to get UsersConnection : {}", id);
        Optional<UsersConnectionDTO> usersConnectionDTO = usersConnectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usersConnectionDTO);
    }

    /**
     * {@code DELETE  /users-connections/:id} : delete the "id" usersConnection.
     *
     * @param id the id of the usersConnectionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users-connections/{id}")
    public ResponseEntity<Void> deleteUsersConnection(@PathVariable Long id) {
        log.debug("REST request to delete UsersConnection : {}", id);

        usersConnectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
