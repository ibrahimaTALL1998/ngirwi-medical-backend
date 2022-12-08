package sn.ngirwi.medical.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ngirwi.medical.domain.SurveillanceSheet;
import sn.ngirwi.medical.repository.SurveillanceSheetRepository;

/**
 * Service Implementation for managing {@link SurveillanceSheet}.
 */
@Service
@Transactional
public class SurveillanceSheetService {

    private final Logger log = LoggerFactory.getLogger(SurveillanceSheetService.class);

    private final SurveillanceSheetRepository surveillanceSheetRepository;

    public SurveillanceSheetService(SurveillanceSheetRepository surveillanceSheetRepository) {
        this.surveillanceSheetRepository = surveillanceSheetRepository;
    }

    /**
     * Save a surveillanceSheet.
     *
     * @param surveillanceSheet the entity to save.
     * @return the persisted entity.
     */
    public SurveillanceSheet save(SurveillanceSheet surveillanceSheet) {
        log.debug("Request to save SurveillanceSheet : {}", surveillanceSheet);
        return surveillanceSheetRepository.save(surveillanceSheet);
    }

    /**
     * Update a surveillanceSheet.
     *
     * @param surveillanceSheet the entity to save.
     * @return the persisted entity.
     */
    public SurveillanceSheet update(SurveillanceSheet surveillanceSheet) {
        log.debug("Request to update SurveillanceSheet : {}", surveillanceSheet);
        return surveillanceSheetRepository.save(surveillanceSheet);
    }

    /**
     * Partially update a surveillanceSheet.
     *
     * @param surveillanceSheet the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SurveillanceSheet> partialUpdate(SurveillanceSheet surveillanceSheet) {
        log.debug("Request to partially update SurveillanceSheet : {}", surveillanceSheet);

        return surveillanceSheetRepository
            .findById(surveillanceSheet.getId())
            .map(existingSurveillanceSheet -> {
                if (surveillanceSheet.getTa() != null) {
                    existingSurveillanceSheet.setTa(surveillanceSheet.getTa());
                }
                if (surveillanceSheet.getTemperature() != null) {
                    existingSurveillanceSheet.setTemperature(surveillanceSheet.getTemperature());
                }
                if (surveillanceSheet.getPulseRate() != null) {
                    existingSurveillanceSheet.setPulseRate(surveillanceSheet.getPulseRate());
                }
                if (surveillanceSheet.getRespiratoryFrequency() != null) {
                    existingSurveillanceSheet.setRespiratoryFrequency(surveillanceSheet.getRespiratoryFrequency());
                }
                if (surveillanceSheet.getRecolorationTime() != null) {
                    existingSurveillanceSheet.setRecolorationTime(surveillanceSheet.getRecolorationTime());
                }
                if (surveillanceSheet.getGlasgow() != null) {
                    existingSurveillanceSheet.setGlasgow(surveillanceSheet.getGlasgow());
                }
                if (surveillanceSheet.getGravityClass() != null) {
                    existingSurveillanceSheet.setGravityClass(surveillanceSheet.getGravityClass());
                }
                if (surveillanceSheet.getHoraryDiuresis() != null) {
                    existingSurveillanceSheet.setHoraryDiuresis(surveillanceSheet.getHoraryDiuresis());
                }
                if (surveillanceSheet.getSpo2() != null) {
                    existingSurveillanceSheet.setSpo2(surveillanceSheet.getSpo2());
                }
                if (surveillanceSheet.getTreatment() != null) {
                    existingSurveillanceSheet.setTreatment(surveillanceSheet.getTreatment());
                }
                if (surveillanceSheet.getHealthEvolution() != null) {
                    existingSurveillanceSheet.setHealthEvolution(surveillanceSheet.getHealthEvolution());
                }
                if (surveillanceSheet.getSheetDate() != null) {
                    existingSurveillanceSheet.setSheetDate(surveillanceSheet.getSheetDate());
                }

                return existingSurveillanceSheet;
            })
            .map(surveillanceSheetRepository::save);
    }

    /**
     * Get all the surveillanceSheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SurveillanceSheet> findAll(Pageable pageable) {
        log.debug("Request to get all SurveillanceSheets");
        return surveillanceSheetRepository.findAll(pageable);
    }

    /**
     * Get one surveillanceSheet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SurveillanceSheet> findOne(Long id) {
        log.debug("Request to get SurveillanceSheet : {}", id);
        return surveillanceSheetRepository.findById(id);
    }

    /**
     * Delete the surveillanceSheet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SurveillanceSheet : {}", id);
        surveillanceSheetRepository.deleteById(id);
    }
}
