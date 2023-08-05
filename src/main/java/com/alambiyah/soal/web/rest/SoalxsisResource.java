package com.alambiyah.soal.web.rest;

import com.alambiyah.soal.repository.SoalxsisRepository;
import com.alambiyah.soal.service.SoalxsisQueryService;
import com.alambiyah.soal.service.SoalxsisService;
import com.alambiyah.soal.service.criteria.SoalxsisCriteria;
import com.alambiyah.soal.service.dto.SoalxsisDTO;
import com.alambiyah.soal.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.alambiyah.soal.domain.Soalxsis}.
 */
@RestController
@RequestMapping("/api")
public class SoalxsisResource {

    private final Logger log = LoggerFactory.getLogger(SoalxsisResource.class);

    private static final String ENTITY_NAME = "soalxsis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoalxsisService soalxsisService;

    private final SoalxsisRepository soalxsisRepository;

    private final SoalxsisQueryService soalxsisQueryService;

    public SoalxsisResource(
        SoalxsisService soalxsisService,
        SoalxsisRepository soalxsisRepository,
        SoalxsisQueryService soalxsisQueryService
    ) {
        this.soalxsisService = soalxsisService;
        this.soalxsisRepository = soalxsisRepository;
        this.soalxsisQueryService = soalxsisQueryService;
    }

    /**
     * {@code POST  /soalxses} : Create a new soalxsis.
     *
     * @param soalxsisDTO the soalxsisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soalxsisDTO, or with status {@code 400 (Bad Request)} if the soalxsis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/soalxses")
    public ResponseEntity<SoalxsisDTO> createSoalxsis(@Valid @RequestBody SoalxsisDTO soalxsisDTO) throws URISyntaxException {
        log.debug("REST request to save Soalxsis : {}", soalxsisDTO);
        if (soalxsisDTO.getId() != null) {
            throw new BadRequestAlertException("A new soalxsis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoalxsisDTO result = soalxsisService.save(soalxsisDTO);
        return ResponseEntity
            .created(new URI("/api/soalxses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /soalxses/:id} : Updates an existing soalxsis.
     *
     * @param id the id of the soalxsisDTO to save.
     * @param soalxsisDTO the soalxsisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soalxsisDTO,
     * or with status {@code 400 (Bad Request)} if the soalxsisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soalxsisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/soalxses/{id}")
    public ResponseEntity<SoalxsisDTO> updateSoalxsis(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SoalxsisDTO soalxsisDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Soalxsis : {}, {}", id, soalxsisDTO);
        if (soalxsisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soalxsisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soalxsisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SoalxsisDTO result = soalxsisService.update(soalxsisDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, soalxsisDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /soalxses/:id} : Partial updates given fields of an existing soalxsis, field will ignore if it is null
     *
     * @param id the id of the soalxsisDTO to save.
     * @param soalxsisDTO the soalxsisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soalxsisDTO,
     * or with status {@code 400 (Bad Request)} if the soalxsisDTO is not valid,
     * or with status {@code 404 (Not Found)} if the soalxsisDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the soalxsisDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/soalxses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SoalxsisDTO> partialUpdateSoalxsis(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SoalxsisDTO soalxsisDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Soalxsis partially : {}, {}", id, soalxsisDTO);
        if (soalxsisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soalxsisDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soalxsisRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SoalxsisDTO> result = soalxsisService.partialUpdate(soalxsisDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, soalxsisDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /soalxses} : get all the soalxses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soalxses in body.
     */
    @GetMapping("/soalxses")
    public ResponseEntity<List<SoalxsisDTO>> getAllSoalxses(
        SoalxsisCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Soalxses by criteria: {}", criteria);
        Page<SoalxsisDTO> page = soalxsisQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /soalxses/count} : count all the soalxses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/soalxses/count")
    public ResponseEntity<Long> countSoalxses(SoalxsisCriteria criteria) {
        log.debug("REST request to count Soalxses by criteria: {}", criteria);
        return ResponseEntity.ok().body(soalxsisQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /soalxses/:id} : get the "id" soalxsis.
     *
     * @param id the id of the soalxsisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soalxsisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/soalxses/{id}")
    public ResponseEntity<SoalxsisDTO> getSoalxsis(@PathVariable Long id) {
        log.debug("REST request to get Soalxsis : {}", id);
        Optional<SoalxsisDTO> soalxsisDTO = soalxsisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(soalxsisDTO);
    }

    /**
     * {@code DELETE  /soalxses/:id} : delete the "id" soalxsis.
     *
     * @param id the id of the soalxsisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/soalxses/{id}")
    public ResponseEntity<Void> deleteSoalxsis(@PathVariable Long id) {
        log.debug("REST request to delete Soalxsis : {}", id);
        soalxsisService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
