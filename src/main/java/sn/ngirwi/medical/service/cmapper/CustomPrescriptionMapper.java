package sn.ngirwi.medical.service.cmapper;

import sn.ngirwi.medical.domain.Consultation;
import sn.ngirwi.medical.domain.Medecine;
import sn.ngirwi.medical.domain.Prescription;
import sn.ngirwi.medical.service.dto.PrescriptionDTO;
import sn.ngirwi.medical.service.mapper.ConsultationMapper;
import sn.ngirwi.medical.service.model.PrescriptionForm;

import java.util.HashSet;
import java.util.Set;

public class CustomPrescriptionMapper {

    private final ConsultationMapper consultationMapper;

    public CustomPrescriptionMapper(ConsultationMapper consultationMapper) {

        this.consultationMapper = consultationMapper;
    }


}
