package et.com.hahu.web.rest;

import et.com.hahu.service.ViewsService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.ViewsDTO;
import et.com.hahu.service.dto.ViewsCriteria;
import et.com.hahu.service.ViewsQueryService;

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
 * REST controller for managing {@link et.com.hahu.domain.Views}.
 */
@RestController
@RequestMapping("/api")
public class ViewsResource {

    private final Logger log = LoggerFactory.getLogger(ViewsResource.class);

    private static final String ENTITY_NAME = "views";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ViewsService viewsService;

    private final ViewsQueryService viewsQueryService;

    public ViewsResource(ViewsService viewsService, ViewsQueryService viewsQueryService) {
        this.viewsService = viewsService;
        this.viewsQueryService = viewsQueryService;
    }

    /**
     * {@code POST  /views} : Create a new views.
     *
     * @param viewsDTO the viewsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new viewsDTO, or with status {@code 400 (Bad Request)} if the views has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/views")
    public ResponseEntity<ViewsDTO> createViews(@RequestBody ViewsDTO viewsDTO) throws URISyntaxException {
        log.debug("REST request to save Views : {}", viewsDTO);
        if (viewsDTO.getId() != null) {
            throw new BadRequestAlertException("A new views cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ViewsDTO result = viewsService.save(viewsDTO);
        return ResponseEntity.created(new URI("/api/views/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /views} : Updates an existing views.
     *
     * @param viewsDTO the viewsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated viewsDTO,
     * or with status {@code 400 (Bad Request)} if the viewsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the viewsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/views")
    public ResponseEntity<ViewsDTO> updateViews(@RequestBody ViewsDTO viewsDTO) throws URISyntaxException {
        log.debug("REST request to update Views : {}", viewsDTO);
        if (viewsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ViewsDTO result = viewsService.save(viewsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, viewsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /views} : get all the views.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of views in body.
     */
    @GetMapping("/views")
    public ResponseEntity<List<ViewsDTO>> getAllViews(ViewsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Views by criteria: {}", criteria);
        Page<ViewsDTO> page = viewsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /views/count} : count all the views.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/views/count")
    public ResponseEntity<Long> countViews(ViewsCriteria criteria) {
        log.debug("REST request to count Views by criteria: {}", criteria);
        return ResponseEntity.ok().body(viewsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /views/:id} : get the "id" views.
     *
     * @param id the id of the viewsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/views/{id}")
    public ResponseEntity<ViewsDTO> getViews(@PathVariable Long id) {
        log.debug("REST request to get Views : {}", id);
        Optional<ViewsDTO> viewsDTO = viewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viewsDTO);
    }

    /**
     * {@code DELETE  /views/:id} : delete the "id" views.
     *
     * @param id the id of the viewsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/views/{id}")
    public ResponseEntity<Void> deleteViews(@PathVariable Long id) {
        log.debug("REST request to delete Views : {}", id);

        viewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
