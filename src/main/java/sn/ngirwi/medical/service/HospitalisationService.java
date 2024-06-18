package sn.ngirwi.medical.service;

import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ngirwi.medical.domain.Hospitalisation;
import sn.ngirwi.medical.domain.SurveillanceSheet;
import sn.ngirwi.medical.domain.enumeration.HospitalisationStatus;
import sn.ngirwi.medical.repository.HospitalisationRepository;
import sn.ngirwi.medical.repository.SurveillanceSheetRepository;
import sn.ngirwi.medical.service.model.HospitalisationForm;

/**
 * Service Implementation for managing {@link Hospitalisation}.
 */
@Service
@Transactional
public class HospitalisationService {

    private final Logger log = LoggerFactory.getLogger(HospitalisationService.class);

    private final HospitalisationRepository hospitalisationRepository;

    private final SurveillanceSheetRepository surveillanceSheetRepository;

    public HospitalisationService(HospitalisationRepository hospitalisationRepository, SurveillanceSheetRepository surveillanceSheetRepository) {
        this.hospitalisationRepository = hospitalisationRepository;
        this.surveillanceSheetRepository = surveillanceSheetRepository;
    }

    /**
     * Save a hospitalisation.
     *
     * @param hospitalisation the entity to save.
     * @return the persisted entity.
     */
    public Hospitalisation save(Hospitalisation hospitalisation) {
        log.debug("Request to save Hospitalisation : {}", hospitalisation);
        return hospitalisationRepository.save(hospitalisation);
    }

    /**
     * Update a hospitalisation.
     *
     * @param hospitalisation the entity to save.
     * @return the persisted entity.
     */
    public Hospitalisation update(Hospitalisation hospitalisation) {
        log.debug("Request to update Hospitalisation : {}", hospitalisation);
        return hospitalisationRepository.save(hospitalisation);
    }

    /**
     * Partially update a hospitalisation.
     *
     * @param hospitalisation the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Hospitalisation> partialUpdate(Hospitalisation hospitalisation) {
        log.debug("Request to partially update Hospitalisation : {}", hospitalisation);

        return hospitalisationRepository
            .findById(hospitalisation.getId())
            .map(existingHospitalisation -> {
                if (hospitalisation.getEntryDate() != null) {
                    existingHospitalisation.setEntryDate(hospitalisation.getEntryDate());
                }
                if (hospitalisation.getReleaseDate() != null) {
                    existingHospitalisation.setReleaseDate(hospitalisation.getReleaseDate());
                }
                if (hospitalisation.getDoctorName() != null) {
                    existingHospitalisation.setDoctorName(hospitalisation.getDoctorName());
                }
                if (hospitalisation.getStatus() != null) {
                    existingHospitalisation.setStatus(hospitalisation.getStatus());
                }

                return existingHospitalisation;
            })
            .map(hospitalisationRepository::save);
    }

    /**
     * Get all the hospitalisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Hospitalisation> findAll(Pageable pageable) {
        log.debug("Request to get all Hospitalisations");
        return hospitalisationRepository.findAll(pageable);
    }

    /**
     * Get one hospitalisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Hospitalisation> findOne(Long id) {
        log.debug("Request to get Hospitalisation : {}", id);
        return hospitalisationRepository.findById(id);
    }

    /**
     * Delete the hospitalisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Hospitalisation : {}", id);
        hospitalisationRepository.deleteById(id);
    }

    public Hospitalisation saveBis(HospitalisationForm form) {
        log.debug("FORM");
        form.toString();

        Hospitalisation h = new Hospitalisation();

        //mapping hospitalisation
        h.setDoctorName(form.getDoctorName());
        h.setPatient(form.getPatient());
        h.setStatus(form.getStatus());
        h.setEntryDate(Instant.parse(form.getEntryDate()));

        //mapping sheet
        SurveillanceSheet s = new SurveillanceSheet();
        s.setGlasgow(form.getGlasgow());
        s.setTa(form.getTa());
        s.setGravityClass(form.getGravityClass());
        s.setPulseRate(form.getPulseRate());
        s.setHoraryDiuresis(form.getHoraryDiuresis());
        s.setTemperature(form.getTemperature());
        s.setHealthEvolution(form.getHealthEvolution());
        s.setPulseRate(form.getPulseRate());
        s.setRespiratoryFrequency(form.getRespiratoryFrequency());
        s.setRecolorationTime(form.getRecolorationTime());
        s.setGlasgow(form.getGlasgow());
        s.setSpo2(form.getSpo2());
        s.setSheetDate(form.getSheetDate());
        s.setHospitalisation(h);

        surveillanceSheetRepository.save(s);

        return hospitalisationRepository.save(h);
    }

    public Optional<Hospitalisation> findPatient(Long id) {
        log.debug("Request to get Hospitalisation in progress for specific patient: {}", id);
        return hospitalisationRepository.findFirstByPatient_IdAndStatus(id, HospitalisationStatus.STARTED);
    }
}
