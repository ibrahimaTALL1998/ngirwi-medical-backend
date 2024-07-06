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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.ngirwi.medical.domain.Hospitalisation;
import sn.ngirwi.medical.repository.HospitalisationRepository;
import sn.ngirwi.medical.security.AuthoritiesConstants;
import sn.ngirwi.medical.service.HospitalisationService;
import sn.ngirwi.medical.service.model.HospitalisationForm;
import sn.ngirwi.medical.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.ngirwi.medical.domain.Hospitalisation}.
 */
@RestController
@RequestMapping("/api")
@PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.DOCTOR + "\")")
public class HospitalisationResource {

    private final Logger log = LoggerFactory.getLogger(HospitalisationResource.class);

    private static final String ENTITY_NAME = "hospitalisation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospitalisationService hospitalisationService;

    private final HospitalisationRepository hospitalisationRepository;

    public HospitalisationResource(HospitalisationService hospitalisationService, HospitalisationRepository hospitalisationRepository) {
        this.hospitalisationService = hospitalisationService;
        this.hospitalisationRepository = hospitalisationRepository;
    }

    /**
     * {@code POST  /hospitalisations} : Create a new hospitalisation.
     *
     * @param hospitalisation the hospitalisation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalisation, or with status {@code 400 (Bad Request)} if the hospitalisation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospitalisations")
    public ResponseEntity<Hospitalisation> createHospitalisation(@RequestBody Hospitalisation hospitalisation) throws URISyntaxException {
        log.debug("REST request to save Hospitalisation : {}", hospitalisation);
        if (hospitalisation.getId() != null) {
            throw new BadRequestAlertException("A new hospitalisation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hospitalisation result = hospitalisationService.save(hospitalisation);
        return ResponseEntity
            .created(new URI("/api/hospitalisations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/hospitalisationsbis")
    public ResponseEntity<Hospitalisation> createHospitalisationBis(@RequestBody HospitalisationForm form) throws URISyntaxException {
        log.debug("REST request to save Hospitalisation form: {}", form);
        // Hospitalisation result = hospitalisationService.saveBis(form);
        Hospitalisation result = hospitalisationService.saveBis(form);
        return ResponseEntity
            .created(new URI("/api/hospitalisationsbis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospitalisations/:id} : Updates an existing hospitalisation.
     *
     * @param id the id of the hospitalisation to save.
     * @param hospitalisation the hospitalisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalisation,
     * or with status {@code 400 (Bad Request)} if the hospitalisation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospitalisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospitalisations/{id}")
    public ResponseEntity<Hospitalisation> updateHospitalisation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hospitalisation hospitalisation
    ) throws URISyntaxException {
        log.debug("REST request to update Hospitalisation : {}, {}", id, hospitalisation);
        if (hospitalisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hospitalisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hospitalisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Hospitalisation result = hospitalisationService.update(hospitalisation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hospitalisation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hospitalisations/:id} : Partial updates given fields of an existing hospitalisation, field will ignore if it is null
     *
     * @param id the id of the hospitalisation to save.
     * @param hospitalisation the hospitalisation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalisation,
     * or with status {@code 400 (Bad Request)} if the hospitalisation is not valid,
     * or with status {@code 404 (Not Found)} if the hospitalisation is not found,
     * or with status {@code 500 (Internal Server Error)} if the hospitalisation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hospitalisations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Hospitalisation> partialUpdateHospitalisation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Hospitalisation hospitalisation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Hospitalisation partially : {}, {}", id, hospitalisation);
        if (hospitalisation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hospitalisation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hospitalisationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Hospitalisation> result = hospitalisationService.partialUpdate(hospitalisation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hospitalisation.getId().toString())
        );
    }

    /**
     * {@code GET  /hospitalisations} : get all the hospitalisations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitalisations in body.
     */
    @GetMapping("/hospitalisations")
    public ResponseEntity<List<Hospitalisation>> getAllHospitalisations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Hospitalisations");
        Page<Hospitalisation> page = hospitalisationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hospitalisations/:id} : get the "id" hospitalisation.
     *
     * @param id the id of the hospitalisation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospitalisation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospitalisations/{id}")
    public ResponseEntity<Hospitalisation> getHospitalisation(@PathVariable Long id) {
        log.debug("REST request to get Hospitalisation : {}", id);
        Optional<Hospitalisation> hospitalisation = hospitalisationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hospitalisation);
    }

    @GetMapping("/hospitalisations-patient/{id}")
    public ResponseEntity<Hospitalisation> getHospitalisationPatientEnCours(@PathVariable Long id) {
        log.debug("REST request to get Hospitalisation for specific patient : {}", id);
        Optional<Hospitalisation> hospitalisation = hospitalisationService.findPatient(id);
        return ResponseUtil.wrapOrNotFound(hospitalisation);
    }

    /**
     * {@code DELETE  /hospitalisations/:id} : delete the "id" hospitalisation.
     *
     * @param id the id of the hospitalisation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospitalisations/{id}")
    public ResponseEntity<Void> deleteHospitalisation(@PathVariable Long id) {
        log.debug("REST request to delete Hospitalisation : {}", id);
        hospitalisationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
