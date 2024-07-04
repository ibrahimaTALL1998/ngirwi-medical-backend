package sn.ngirwi.medical.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ngirwi.medical.domain.Consultation;
import sn.ngirwi.medical.domain.Medecine;
import sn.ngirwi.medical.domain.Patient;
import sn.ngirwi.medical.domain.Prescription;
import sn.ngirwi.medical.repository.MedecineRepository;
import sn.ngirwi.medical.repository.PrescriptionRepository;
import sn.ngirwi.medical.service.dto.MedecineDTO;
import sn.ngirwi.medical.service.dto.PrescriptionDTO;
import sn.ngirwi.medical.service.mapper.ConsultationMapper;
import sn.ngirwi.medical.service.mapper.PrescriptionMapper;
import sn.ngirwi.medical.service.model.PrescriptionForm;

/**
 * Service Implementation for managing {@link Prescription}.
 */
@Service
@Transactional
public class PrescriptionService {

    private final Logger log = LoggerFactory.getLogger(PrescriptionService.class);

    private final PrescriptionRepository prescriptionRepository;

    private final PrescriptionMapper prescriptionMapper;

    private final ConsultationMapper consultationMapper;

    private final MedecineRepository medecineRepository;


    public PrescriptionService(PrescriptionRepository prescriptionRepository, PrescriptionMapper prescriptionMapper, ConsultationMapper consultationMapper, MedecineRepository medecineRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.prescriptionMapper = prescriptionMapper;
        this.consultationMapper = consultationMapper;
        this.medecineRepository = medecineRepository;
    }

    /**
     * Save a prescription.
     *
     * @param prescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescriptionDTO save(PrescriptionDTO prescriptionDTO) {
        log.debug("Request to save Prescription : {}", prescriptionDTO);
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toDto(prescription);
    }

    /**
     * Update a prescription.
     *
     * @param prescriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public PrescriptionDTO update(PrescriptionDTO prescriptionDTO) {
        log.debug("Request to update Prescription : {}", prescriptionDTO);
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        prescription = prescriptionRepository.save(prescription);
        return prescriptionMapper.toDto(prescription);
    }

    /**
     * Partially update a prescription.
     *
     * @param prescriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrescriptionDTO> partialUpdate(PrescriptionDTO prescriptionDTO) {
        log.debug("Request to partially update Prescription : {}", prescriptionDTO);

        return prescriptionRepository
            .findById(prescriptionDTO.getId())
            .map(existingPrescription -> {
                prescriptionMapper.partialUpdate(existingPrescription, prescriptionDTO);

                return existingPrescription;
            })
            .map(prescriptionRepository::save)
            .map(prescriptionMapper::toDto);
    }

    /**
     * Get all the prescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrescriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll(pageable).map(prescriptionMapper::toDto);
    }

    /**
     * Get one prescription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrescriptionDTO> findOne(Long id) {
        log.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findById(id).map(prescriptionMapper::toDto);
    }

    /**
     * Delete the prescription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Prescription : {}", id);

        List<Medecine> medecines = medecineRepository.findByOrdonance_Id(id);

        for (Medecine m :
            medecines) {
            medecineRepository.deleteById(m.getId());
        }
        prescriptionRepository.deleteById(id);
    }


    public Prescription map(PrescriptionDTO prescriptionDTO) {
        Prescription p = new Prescription();
        //p.setId(prescriptionDTO.getId());
        p.setAuthor(prescriptionDTO.getAuthor());
        p.setCreationDate(prescriptionDTO.getCreationDate());
        Consultation c = consultationMapper.toEntity(prescriptionDTO.getConsultation());
        p.setConsultation(c);
        Set<Medecine> medecines = medecineMapper(prescriptionDTO);
        for (Medecine m : medecines) {
            m.setOrdonance(p);
        }
        p.setMedecines(medecines);
        return p;
    }

    public Set<Medecine> medecineMapper(PrescriptionDTO prescriptionDTO) {
        Set<Medecine> s = new HashSet<>();
        //for (int i = 0; i < prescriptionDTO.getMedecines().toArray().length; i++){
        for (PrescriptionForm f : prescriptionDTO.getMedecines()) {
            Medecine m = new Medecine();
            //m.setId(prescriptionDTO.getId() + i);
            m.setName(f.getName());
            m.setDuration(Long.valueOf(f.getDuration()));
            m.setFrequency(Double.valueOf(f.getFrequency()));
            s.add(m);
        }
        //}

        return s;
    }

    public PrescriptionDTO saveBis(PrescriptionDTO prescriptionDTO) {
        log.debug("Request to save Prescription : {}", prescriptionDTO);

        Prescription prescription = map(prescriptionDTO); // Map DTO to entity

        // Assuming prescriptionDTO has medicines mapped correctly
        for (Medecine medecine : prescription.getMedecines()) {
            medecine.setOrdonance(prescription); // Associate medicine with prescription
            // Remove manual save if cascade persist is configured
            medecineRepository.save(medecine); // Remove this line if cascade persist is configured
        }

        // Save prescription (and associated medicines if cascade persist is configured)
        prescription = prescriptionRepository.save(prescription);

        log.debug(prescription.toString());

        prescriptionDTO.setId(prescription.getId());

        //return prescriptionMapper.toDto(prescription); // Map entity back to DTO
        return prescriptionDTO; // Map entity back to DTO
    }


    public PrescriptionDTO updateBis(PrescriptionDTO prescriptionDTO) {

        log.debug("Request to update Prescription : {}", prescriptionDTO);
        Prescription prescription = prescriptionMapper.toEntity(prescriptionDTO);
        log.debug("VALUES : {}", prescription);
        log.debug("FORM : {}", prescription.getMedecines());

        //prescription = prescriptionRepository.save(prescription);

        for (Medecine medecine : prescription.getMedecines()) {
            if (prescription.getId() != null && medecineRepository.existsByNameAndDurationAndFrequencyAndOrdonance_Id(medecine.getName(), medecine.getDuration(), medecine.getFrequency(), prescriptionDTO.getId())) {
                Medecine medecine1 = medecineRepository.findByNameAndDurationAndFrequencyAndOrdonance_Id(medecine.getName(), medecine.getDuration(), medecine.getFrequency(), prescriptionDTO.getId());
                medecineRepository.deleteById(medecine1.getId());
            }
            medecine.setOrdonance(prescription);
            medecineRepository.save(medecine);
        }

        //return prescriptionMapper.toDto(prescription);
        return prescriptionDTO;
    }
}
