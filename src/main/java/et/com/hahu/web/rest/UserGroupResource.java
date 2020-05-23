package et.com.hahu.web.rest;

import et.com.hahu.service.UserGroupService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.UserGroupDTO;
import et.com.hahu.service.dto.UserGroupCriteria;
import et.com.hahu.service.UserGroupQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.UserGroup}.
 */
@RestController
@RequestMapping("/api")
public class UserGroupResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupResource.class);

    private static final String ENTITY_NAME = "userGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupService userGroupService;

    private final UserGroupQueryService userGroupQueryService;

    public UserGroupResource(UserGroupService userGroupService, UserGroupQueryService userGroupQueryService) {
        this.userGroupService = userGroupService;
        this.userGroupQueryService = userGroupQueryService;
    }

    /**
     * {@code POST  /user-groups} : Create a new userGroup.
     *
     * @param userGroupDTO the userGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupDTO, or with status {@code 400 (Bad Request)} if the userGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-groups")
    public ResponseEntity<UserGroupDTO> createUserGroup(@RequestBody UserGroupDTO userGroupDTO) throws URISyntaxException {
        log.debug("REST request to save UserGroup : {}", userGroupDTO);
        if (userGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new userGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupDTO result = userGroupService.save(userGroupDTO);
        return ResponseEntity.created(new URI("/api/user-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-groups} : Updates an existing userGroup.
     *
     * @param userGroupDTO the userGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupDTO,
     * or with status {@code 400 (Bad Request)} if the userGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-groups")
    public ResponseEntity<UserGroupDTO> updateUserGroup(@RequestBody UserGroupDTO userGroupDTO) throws URISyntaxException {
        log.debug("REST request to update UserGroup : {}", userGroupDTO);
        if (userGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGroupDTO result = userGroupService.save(userGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-groups} : get all the userGroups.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroups in body.
     */
    @GetMapping("/user-groups")
    public ResponseEntity<List<UserGroupDTO>> getAllUserGroups(UserGroupCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserGroups by criteria: {}", criteria);
        Page<UserGroupDTO> page = userGroupQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-groups/count} : count all the userGroups.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-groups/count")
    public ResponseEntity<Long> countUserGroups(UserGroupCriteria criteria) {
        log.debug("REST request to count UserGroups by criteria: {}", criteria);
        return ResponseEntity.ok().body(userGroupQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-groups/:id} : get the "id" userGroup.
     *
     * @param id the id of the userGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-groups/{id}")
    public ResponseEntity<UserGroupDTO> getUserGroup(@PathVariable Long id) {
        log.debug("REST request to get UserGroup : {}", id);
        Optional<UserGroupDTO> userGroupDTO = userGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGroupDTO);
    }

    /**
     * {@code DELETE  /user-groups/:id} : delete the "id" userGroup.
     *
     * @param id the id of the userGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-groups/{id}")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable Long id) {
        log.debug("REST request to delete UserGroup : {}", id);

        userGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
