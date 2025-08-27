package sn.ngirwi.medical.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ngirwi.medical.domain.*;
import sn.ngirwi.medical.domain.enumeration.HospitalisationStatus;
import sn.ngirwi.medical.repository.*;
import sn.ngirwi.medical.service.dto.HospitalisationDTO;
import sn.ngirwi.medical.service.mapper.HospitalisationMapper;

@Service
@Transactional
public class HospitalisationService {

    private final Logger log = LoggerFactory.getLogger(HospitalisationService.class);

    private final HospitalisationRepository hospitalisationRepository;
    private final SurveillanceSheetRepository surveillanceSheetRepository;
    private final PatientRepository patientRepository;
    private final DossierMedicalRepository dossierMedicalRepository;
    private final BillRepository billRepository;
    private final HospitalisationMapper hospitalisationMapper;

    public HospitalisationService(
        HospitalisationRepository hospitalisationRepository,
        SurveillanceSheetRepository surveillanceSheetRepository,
        PatientRepository patientRepository,
        DossierMedicalRepository dossierMedicalRepository,
        BillRepository billRepository,
        HospitalisationMapper hospitalisationMapper
    ) {
        this.hospitalisationRepository = hospitalisationRepository;
        this.surveillanceSheetRepository = surveillanceSheetRepository;
        this.patientRepository = patientRepository;
        this.dossierMedicalRepository = dossierMedicalRepository;
        this.billRepository = billRepository;
        this.hospitalisationMapper = hospitalisationMapper;
    }

    // -------------------------
    // Create
    // -------------------------
    public HospitalisationDTO save(HospitalisationDTO dto) {
        Objects.requireNonNull(dto, "hospitalisation DTO must not be null");

        if (dto.getPatientId() == null) {
            throw new IllegalArgumentException("patientId is required");
        }
        // Validate patient exists
        Patient patient = patientRepository
            .findById(dto.getPatientId())
            .orElseThrow(() -> new IllegalArgumentException("Patient not found id=" + dto.getPatientId()));

        // Prevent multiple STARTED hospitalisations for same patient
        if (
            HospitalisationStatus.STARTED.equals(dto.getStatus()) &&
            hospitalisationRepository.existsByPatientIdAndStatus(dto.getPatientId(), HospitalisationStatus.STARTED)
        ) {
            throw new IllegalArgumentException("Patient already has an active hospitalisation");
        }

        // Map to entity (mapper will set patient via fromPatientId)
        Hospitalisation entity = hospitalisationMapper.toEntity(dto);

        // Ensure required business fields
        if (entity.getDoctorName() == null || entity.getDoctorName().isBlank()) {
            throw new IllegalArgumentException("Doctor name is required");
        }

        // Handle dossierMedical association:
        if (dto.getDossierMedicalId() != null) {
            if (!dossierMedicalRepository.existsById(dto.getDossierMedicalId())) {
                throw new IllegalArgumentException("DossierMedical not found id=" + dto.getDossierMedicalId());
            }
            entity.setDossierMedical(hospitalisationMapper.fromDossierMedicalId(dto.getDossierMedicalId()));
        } else {
            // attach patient's dossierMedical if present
            if (patient.getDossierMedical() != null) {
                entity.setDossierMedical(patient.getDossierMedical());
            }
        }

        // Attach surveillance sheets if provided (by ids)
        if (dto.getSurveillanceSheetIds() != null && !dto.getSurveillanceSheetIds().isEmpty()) {
            Set<SurveillanceSheet> sheets = fetchAndValidateSurveillanceSheetsForAttach(dto.getSurveillanceSheetIds(), null);
            // assign hospitalisation reference on them AFTER save (or set now to entity)
            sheets.forEach(s -> s.setHospitalisation(entity));
            entity.setSurveillanceSheets(sheets);
        }

        // Default entryDate
        if (entity.getEntryDate() == null) {
            entity.setEntryDate(Instant.now());
        }

        if (entity.getReleaseDate() != null && entity.getEntryDate() != null && entity.getReleaseDate().isBefore(entity.getEntryDate())) {
            throw new IllegalArgumentException("releaseDate cannot be before entryDate");
        }

        if (entity.getReleaseDate() != null) {
            entity.setStatus(HospitalisationStatus.DONE);
        } else if (entity.getStatus() == null) {
            entity.setStatus(HospitalisationStatus.STARTED);
        }

        try {
            Hospitalisation saved = hospitalisationRepository.save(entity);
            return hospitalisationMapper.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Database constraint violated when saving hospitalisation: " + ex.getMessage(), ex);
        }
    }

    // -------------------------
    // Update (full)
    // -------------------------
    public HospitalisationDTO update(HospitalisationDTO dto) {
        Objects.requireNonNull(dto, "hospitalisation DTO must not be null");
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id is required for update");
        }

        Hospitalisation existing = hospitalisationRepository
            .findById(dto.getId())
            .orElseThrow(() -> new NoSuchElementException("Hospitalisation not found id=" + dto.getId()));

        // Patient change is not allowed
        if (
            dto.getPatientId() != null &&
            existing.getPatient() != null &&
            !Objects.equals(dto.getPatientId(), existing.getPatient().getId())
        ) {
            throw new IllegalArgumentException("Changing patient of an existing hospitalisation is not allowed");
        }

        // Map DTO -> entity (mapper ignores surveillanceSheets; we'll handle them)
        Hospitalisation toSave = hospitalisationMapper.toEntity(dto);
        toSave.setId(existing.getId());

        // Preserve patient object reference
        if (existing.getPatient() != null) {
            toSave.setPatient(existing.getPatient());
        } else if (dto.getPatientId() != null) {
            Patient p = new Patient();
            p.setId(dto.getPatientId());
            toSave.setPatient(p);
        }

        // Handle dossierMedical association
        if (dto.getDossierMedicalId() != null) {
            if (!dossierMedicalRepository.existsById(dto.getDossierMedicalId())) {
                throw new IllegalArgumentException("DossierMedical not found id=" + dto.getDossierMedicalId());
            }
            toSave.setDossierMedical(hospitalisationMapper.fromDossierMedicalId(dto.getDossierMedicalId()));
        } else {
            // preserve existing dossierMedical
            toSave.setDossierMedical(existing.getDossierMedical());
        }

        // Handle surveillance sheets replacement (if provided)
        if (dto.getSurveillanceSheetIds() != null) {
            Set<SurveillanceSheet> sheets = fetchAndValidateSurveillanceSheetsForAttach(dto.getSurveillanceSheetIds(), existing.getId());
            sheets.forEach(s -> s.setHospitalisation(toSave));
            toSave.setSurveillanceSheets(sheets);
        } else {
            toSave.setSurveillanceSheets(existing.getSurveillanceSheets());
        }

        // Validate dates
        if (toSave.getReleaseDate() != null && toSave.getEntryDate() != null && toSave.getReleaseDate().isBefore(toSave.getEntryDate())) {
            throw new IllegalArgumentException("releaseDate cannot be before entryDate");
        }

        try {
            Hospitalisation saved = hospitalisationRepository.save(toSave);
            return hospitalisationMapper.toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Database constraint violated when updating hospitalisation: " + ex.getMessage(), ex);
        }
    }

    // -------------------------
    // Partial update (PATCH)
    // -------------------------
    public Optional<HospitalisationDTO> partialUpdate(HospitalisationDTO dto) {
        Objects.requireNonNull(dto, "hospitalisation DTO must not be null");
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id is required for partial update");
        }

        return hospitalisationRepository
            .findById(dto.getId())
            .map(existing -> {
                if (dto.getEntryDate() != null) existing.setEntryDate(dto.getEntryDate());
                if (dto.getReleaseDate() != null) {
                    if (existing.getEntryDate() != null && dto.getReleaseDate().isBefore(existing.getEntryDate())) {
                        throw new IllegalArgumentException("releaseDate cannot be before entryDate");
                    }
                    existing.setReleaseDate(dto.getReleaseDate());
                    existing.setStatus(HospitalisationStatus.DONE);
                }
                if (dto.getDoctorName() != null) {
                    if (dto.getDoctorName().isBlank()) {
                        throw new IllegalArgumentException("doctorName cannot be blank");
                    }
                    existing.setDoctorName(dto.getDoctorName());
                }
                if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
                if (dto.getAdmissionReason() != null) existing.setAdmissionReason(dto.getAdmissionReason());
                if (dto.getEntryDiagnosis() != null) existing.setEntryDiagnosis(dto.getEntryDiagnosis());
                if (dto.getFinalDiagnosis() != null) existing.setFinalDiagnosis(dto.getFinalDiagnosis());
                if (dto.getService() != null) existing.setService(dto.getService());

                // dossierMedical: replace only if explicitly provided
                if (dto.getDossierMedicalId() != null) {
                    if (!dossierMedicalRepository.existsById(dto.getDossierMedicalId())) {
                        throw new IllegalArgumentException("DossierMedical not found id=" + dto.getDossierMedicalId());
                    }
                    existing.setDossierMedical(hospitalisationMapper.fromDossierMedicalId(dto.getDossierMedicalId()));
                }

                if (dto.getSurveillanceSheetIds() != null) {
                    Set<SurveillanceSheet> sheets = fetchAndValidateSurveillanceSheetsForAttach(
                        dto.getSurveillanceSheetIds(),
                        existing.getId()
                    );
                    sheets.forEach(s -> s.setHospitalisation(existing));
                    existing.setSurveillanceSheets(sheets);
                }

                return existing;
            })
            .map(hospitalisationRepository::save)
            .map(hospitalisationMapper::toDto);
    }

    // -------------------------
    // Finders
    // -------------------------
    @Transactional(readOnly = true)
    public Page<HospitalisationDTO> findAll(Pageable pageable) {
        return hospitalisationRepository.findAll(pageable).map(hospitalisationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<HospitalisationDTO> findOne(Long id) {
        return hospitalisationRepository.findById(id).map(hospitalisationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<HospitalisationDTO> findByPatient(Long patientId, String service, String statusStr, Pageable pageable) {
        HospitalisationStatus status = null;
        if (statusStr != null) {
            try {
                status = HospitalisationStatus.valueOf(statusStr);
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Invalid status: " + statusStr);
            }
        }
        return hospitalisationRepository.search(patientId, status, null, null, pageable).map(hospitalisationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<HospitalisationDTO> findActive(String service, Pageable pageable) {
        HospitalisationStatus started = HospitalisationStatus.STARTED;
        Page<Hospitalisation> page = hospitalisationRepository.search(null, started, null, null, pageable);
        if (service == null) {
            return page.map(hospitalisationMapper::toDto);
        } else {
            List<HospitalisationDTO> filtered = page
                .stream()
                .filter(h -> service.equals(h.getService()))
                .map(hospitalisationMapper::toDto)
                .collect(Collectors.toList());
            return new org.springframework.data.domain.PageImpl<>(filtered, pageable, filtered.size());
        }
    }

    @Transactional(readOnly = true)
    public Page<HospitalisationDTO> search(Long patientId, String statusStr, Instant from, Instant to, String service, Pageable pageable) {
        HospitalisationStatus status = null;
        if (statusStr != null) {
            try {
                status = HospitalisationStatus.valueOf(statusStr);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + statusStr);
            }
        }
        Page<Hospitalisation> page = hospitalisationRepository.search(patientId, status, from, to, pageable);
        if (service == null) {
            return page.map(hospitalisationMapper::toDto);
        } else {
            List<HospitalisationDTO> filtered = page
                .stream()
                .filter(h -> service.equals(h.getService()))
                .map(hospitalisationMapper::toDto)
                .collect(Collectors.toList());
            return new org.springframework.data.domain.PageImpl<>(filtered, pageable, filtered.size());
        }
    }

    // -------------------------
    // Close hospitalisation (and generate a Bill optionally)
    // -------------------------
    public HospitalisationDTO close(Long id, Instant releaseDate, String finalDiagnosis, boolean generateBill) {
        Hospitalisation existing = hospitalisationRepository
            .findById(id)
            .orElseThrow(() -> new NoSuchElementException("Hospitalisation not found id=" + id));

        if (existing.getStatus() == HospitalisationStatus.DONE) {
            throw new IllegalStateException("Hospitalisation already closed");
        }

        if (releaseDate == null) {
            throw new IllegalArgumentException("releaseDate is required");
        }
        if (existing.getEntryDate() != null && releaseDate.isBefore(existing.getEntryDate())) {
            throw new IllegalArgumentException("releaseDate cannot be before entryDate");
        }

        existing.setReleaseDate(releaseDate);
        if (finalDiagnosis != null) existing.setFinalDiagnosis(finalDiagnosis);
        existing.setStatus(HospitalisationStatus.DONE);

        Hospitalisation saved = hospitalisationRepository.save(existing);

        if (generateBill) {
            createBillForHospitalisation(saved);
        }

        return hospitalisationMapper.toDto(saved);
    }

    // -------------------------
    // Delete
    // -------------------------
    public void delete(Long id) {
        if (!hospitalisationRepository.existsById(id)) {
            throw new NoSuchElementException("Hospitalisation not found id=" + id);
        }
        hospitalisationRepository.deleteById(id);
    }

    // -------------------------
    // Helpers
    // -------------------------
    private Set<SurveillanceSheet> fetchAndValidateSurveillanceSheetsForAttach(Set<Long> ids, Long targetHospitalisationId) {
        if (ids == null || ids.isEmpty()) return Collections.emptySet();

        Iterable<SurveillanceSheet> found = surveillanceSheetRepository.findAllById(ids);
        Set<SurveillanceSheet> sheets = new HashSet<>();
        found.forEach(sheets::add);

        Set<Long> foundIds = sheets.stream().map(SurveillanceSheet::getId).collect(Collectors.toSet());
        Set<Long> missing = ids.stream().filter(id -> !foundIds.contains(id)).collect(Collectors.toSet());
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("SurveillanceSheet ids not found: " + missing);
        }

        for (SurveillanceSheet s : sheets) {
            if (s.getHospitalisation() != null) {
                Long attachedHospId = s.getHospitalisation().getId();
                if (targetHospitalisationId == null || !Objects.equals(attachedHospId, targetHospitalisationId)) {
                    throw new IllegalArgumentException(
                        "SurveillanceSheet id=" + s.getId() + " is already attached to hospitalisation id=" + attachedHospId
                    );
                }
            }
        }

        return sheets;
    }

    private void createBillForHospitalisation(Hospitalisation hospitalisation) {
        if (hospitalisation.getPatient() == null || hospitalisation.getPatient().getId() == null) {
            throw new IllegalArgumentException("Cannot create bill: hospitalisation has no associated patient");
        }

        Bill bill = new Bill();
        bill.setDate(Instant.now());
        bill.setAuthor(hospitalisation.getDoctorName());
        bill.setPatient(hospitalisation.getPatient());
        bill.setDesc("Facturation pour hospitalisation id=" + hospitalisation.getId());
        bill.setTotal(BigDecimal.ZERO);

        billRepository.save(bill);
        log.debug("Created Bill id={} for hospitalisation id={}", bill.getId(), hospitalisation.getId());
    }
}
