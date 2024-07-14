package sn.ngirwi.medical.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.ngirwi.medical.domain.SurveillanceSheet;
import sn.ngirwi.medical.repository.SurveillanceSheetRepository;
import sn.ngirwi.medical.service.SurveillanceSheetService;
import sn.ngirwi.medical.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.ngirwi.medical.domain.SurveillanceSheet}.
 */
@RestController
@RequestMapping("/api")
public class SurveillanceSheetResource {

    private final Logger log = LoggerFactory.getLogger(SurveillanceSheetResource.class);

    private static final String ENTITY_NAME = "surveillanceSheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SurveillanceSheetService surveillanceSheetService;

    private final SurveillanceSheetRepository surveillanceSheetRepository;

    public SurveillanceSheetResource(
        SurveillanceSheetService surveillanceSheetService,
        SurveillanceSheetRepository surveillanceSheetRepository
    ) {
        this.surveillanceSheetService = surveillanceSheetService;
        this.surveillanceSheetRepository = surveillanceSheetRepository;
    }

    /**
     * {@code POST  /surveillance-sheets} : Create a new surveillanceSheet.
     *
     * @param surveillanceSheet the surveillanceSheet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new surveillanceSheet, or with status {@code 400 (Bad Request)} if the surveillanceSheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/surveillance-sheets")
    public ResponseEntity<SurveillanceSheet> createSurveillanceSheet(@RequestBody SurveillanceSheet surveillanceSheet)
        throws URISyntaxException {
        log.debug("REST request to save SurveillanceSheet : {}", surveillanceSheet);
        if (surveillanceSheet.getId() != null) {
            throw new BadRequestAlertException("A new surveillanceSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveillanceSheet result = surveillanceSheetService.save(surveillanceSheet);
        return ResponseEntity
            .created(new URI("/api/surveillance-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /surveillance-sheets/:id} : Updates an existing surveillanceSheet.
     *
     * @param id the id of the surveillanceSheet to save.
     * @param surveillanceSheet the surveillanceSheet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveillanceSheet,
     * or with status {@code 400 (Bad Request)} if the surveillanceSheet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the surveillanceSheet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/surveillance-sheets/{id}")
    public ResponseEntity<SurveillanceSheet> updateSurveillanceSheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SurveillanceSheet surveillanceSheet
    ) throws URISyntaxException {
        log.debug("REST request to update SurveillanceSheet : {}, {}", id, surveillanceSheet);
        if (surveillanceSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveillanceSheet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveillanceSheetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SurveillanceSheet result = surveillanceSheetService.update(surveillanceSheet);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, surveillanceSheet.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /surveillance-sheets/:id} : Partial updates given fields of an existing surveillanceSheet, field will ignore if it is null
     *
     * @param id the id of the surveillanceSheet to save.
     * @param surveillanceSheet the surveillanceSheet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated surveillanceSheet,
     * or with status {@code 400 (Bad Request)} if the surveillanceSheet is not valid,
     * or with status {@code 404 (Not Found)} if the surveillanceSheet is not found,
     * or with status {@code 500 (Internal Server Error)} if the surveillanceSheet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/surveillance-sheets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SurveillanceSheet> partialUpdateSurveillanceSheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SurveillanceSheet surveillanceSheet
    ) throws URISyntaxException {
        log.debug("REST request to partial update SurveillanceSheet partially : {}, {}", id, surveillanceSheet);
        if (surveillanceSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, surveillanceSheet.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!surveillanceSheetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SurveillanceSheet> result = surveillanceSheetService.partialUpdate(surveillanceSheet);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, surveillanceSheet.getId().toString())
        );
    }

    /**
     * {@code GET  /surveillance-sheets} : get all the surveillanceSheets.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of surveillanceSheets in body.
     */
    @GetMapping("/surveillance-sheets")
    public ResponseEntity<List<SurveillanceSheet>> getAllSurveillanceSheets(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of SurveillanceSheets");
        Page<SurveillanceSheet> page = surveillanceSheetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /surveillance-sheets/:id} : get the "id" surveillanceSheet.
     *
     * @param id the id of the surveillanceSheet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the surveillanceSheet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/surveillance-sheets/{id}")
    public ResponseEntity<SurveillanceSheet> getSurveillanceSheet(@PathVariable Long id) {
        log.debug("REST request to get SurveillanceSheet : {}", id);
        Optional<SurveillanceSheet> surveillanceSheet = surveillanceSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surveillanceSheet);
    }

    /**
     * {@code DELETE  /surveillance-sheets/:id} : delete the "id" surveillanceSheet.
     *
     * @param id the id of the surveillanceSheet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/surveillance-sheets/{id}")
    public ResponseEntity<Void> deleteSurveillanceSheet(@PathVariable Long id) {
        log.debug("REST request to delete SurveillanceSheet : {}", id);
        surveillanceSheetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
