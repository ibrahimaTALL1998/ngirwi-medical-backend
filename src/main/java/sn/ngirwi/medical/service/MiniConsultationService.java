package sn.ngirwi.medical.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ngirwi.medical.domain.MiniConsultation;
import sn.ngirwi.medical.repository.MiniConsultationRepository;
import sn.ngirwi.medical.repository.SurveillanceSheetRepository;
import sn.ngirwi.medical.service.dto.MiniConsultationDTO;
import sn.ngirwi.medical.service.mapper.MiniConsultationMapper;

@Service
@Transactional
public class MiniConsultationService {

    private final MiniConsultationRepository miniConsultationRepository;
    private final SurveillanceSheetRepository surveillanceSheetRepository;
    private final MiniConsultationMapper miniConsultationMapper;

    public MiniConsultationService(
        MiniConsultationRepository miniConsultationRepository,
        SurveillanceSheetRepository surveillanceSheetRepository,
        MiniConsultationMapper miniConsultationMapper
    ) {
        this.miniConsultationRepository = miniConsultationRepository;
        this.surveillanceSheetRepository = surveillanceSheetRepository;
        this.miniConsultationMapper = miniConsultationMapper;
    }

    public MiniConsultationDTO save(MiniConsultationDTO dto) {
        // Vérifier si la SurveillanceSheet existe
        if (dto.getSurveillanceSheetId() != null && !surveillanceSheetRepository.existsById(dto.getSurveillanceSheetId())) {
            throw new IllegalArgumentException("Invalid surveillanceSheetId: " + dto.getSurveillanceSheetId());
        }

        MiniConsultation entity = miniConsultationMapper.toEntity(dto);
        entity = miniConsultationRepository.save(entity);
        return miniConsultationMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public Optional<MiniConsultationDTO> findOne(Long id) {
        return miniConsultationRepository.findById(id).map(miniConsultationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MiniConsultationDTO> findAll() {
        return miniConsultationRepository.findAll().stream().map(miniConsultationMapper::toDto).collect(Collectors.toList());
    }

    public void delete(Long id) {
        miniConsultationRepository.deleteById(id);
    }

    /**
     * Chercher la mini-consultation liée à une surveillance sheet.
     */
    @Transactional(readOnly = true)
    public Optional<MiniConsultationDTO> findBySurveillanceSheet(Long surveillanceSheetId) {
        return miniConsultationRepository
            .findAll()
            .stream()
            .filter(mc -> mc.getSurveillanceSheet() != null && mc.getSurveillanceSheet().getId().equals(surveillanceSheetId))
            .findFirst()
            .map(miniConsultationMapper::toDto);
    }
}
