package et.com.hahu.web.rest;

import et.com.hahu.service.AdditionalUserInfoService;
import et.com.hahu.web.rest.errors.BadRequestAlertException;
import et.com.hahu.service.dto.AdditionalUserInfoDTO;
import et.com.hahu.service.dto.AdditionalUserInfoCriteria;
import et.com.hahu.service.AdditionalUserInfoQueryService;

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
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link et.com.hahu.domain.AdditionalUserInfo}.
 */
@RestController
@RequestMapping("/api")
public class AdditionalUserInfoResource {

    private final Logger log = LoggerFactory.getLogger(AdditionalUserInfoResource.class);

    private static final String ENTITY_NAME = "additionalUserInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdditionalUserInfoService additionalUserInfoService;

    private final AdditionalUserInfoQueryService additionalUserInfoQueryService;

    public AdditionalUserInfoResource(AdditionalUserInfoService additionalUserInfoService, AdditionalUserInfoQueryService additionalUserInfoQueryService) {
        this.additionalUserInfoService = additionalUserInfoService;
        this.additionalUserInfoQueryService = additionalUserInfoQueryService;
    }

    /**
     * {@code POST  /additional-user-infos} : Create a new additionalUserInfo.
     *
     * @param additionalUserInfoDTO the additionalUserInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new additionalUserInfoDTO, or with status {@code 400 (Bad Request)} if the additionalUserInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/additional-user-infos")
    public ResponseEntity<AdditionalUserInfoDTO> createAdditionalUserInfo(@Valid @RequestBody AdditionalUserInfoDTO additionalUserInfoDTO) throws URISyntaxException {
        log.debug("REST request to save AdditionalUserInfo : {}", additionalUserInfoDTO);
        if (additionalUserInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new additionalUserInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(additionalUserInfoDTO.getUserId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        AdditionalUserInfoDTO result = additionalUserInfoService.save(additionalUserInfoDTO);
        return ResponseEntity.created(new URI("/api/additional-user-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /additional-user-infos} : Updates an existing additionalUserInfo.
     *
     * @param additionalUserInfoDTO the additionalUserInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated additionalUserInfoDTO,
     * or with status {@code 400 (Bad Request)} if the additionalUserInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the additionalUserInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/additional-user-infos")
    public ResponseEntity<AdditionalUserInfoDTO> updateAdditionalUserInfo(@Valid @RequestBody AdditionalUserInfoDTO additionalUserInfoDTO) throws URISyntaxException {
        log.debug("REST request to update AdditionalUserInfo : {}", additionalUserInfoDTO);
        if (additionalUserInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdditionalUserInfoDTO result = additionalUserInfoService.save(additionalUserInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, additionalUserInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /additional-user-infos} : get all the additionalUserInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of additionalUserInfos in body.
     */
    @GetMapping("/additional-user-infos")
    public ResponseEntity<List<AdditionalUserInfoDTO>> getAllAdditionalUserInfos(AdditionalUserInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AdditionalUserInfos by criteria: {}", criteria);
        Page<AdditionalUserInfoDTO> page = additionalUserInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /additional-user-infos/count} : count all the additionalUserInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/additional-user-infos/count")
    public ResponseEntity<Long> countAdditionalUserInfos(AdditionalUserInfoCriteria criteria) {
        log.debug("REST request to count AdditionalUserInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(additionalUserInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /additional-user-infos/:id} : get the "id" additionalUserInfo.
     *
     * @param id the id of the additionalUserInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the additionalUserInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/additional-user-infos/{id}")
    public ResponseEntity<AdditionalUserInfoDTO> getAdditionalUserInfo(@PathVariable Long id) {
        log.debug("REST request to get AdditionalUserInfo : {}", id);
        Optional<AdditionalUserInfoDTO> additionalUserInfoDTO = additionalUserInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(additionalUserInfoDTO);
    }

    /**
     * {@code DELETE  /additional-user-infos/:id} : delete the "id" additionalUserInfo.
     *
     * @param id the id of the additionalUserInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/additional-user-infos/{id}")
    public ResponseEntity<Void> deleteAdditionalUserInfo(@PathVariable Long id) {
        log.debug("REST request to delete AdditionalUserInfo : {}", id);
        additionalUserInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
