package sn.ngirwi.medical.service.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;
import sn.ngirwi.medical.domain.*;
import sn.ngirwi.medical.service.dto.SurveillanceSheetDTO;

/**
 * Mapper for the entity {@link SurveillanceSheet} and its DTO {@link SurveillanceSheetDTO}.
 */
@Mapper(componentModel = "spring")
public interface SurveillanceSheetMapper extends EntityMapper<SurveillanceSheetDTO, SurveillanceSheet> {
    @Mapping(source = "hospitalisation.id", target = "hospitalisationId")
    @Mapping(source = "miniConsultation.id", target = "miniConsultationId")
    @Mapping(target = "prescriptionIds", expression = "java(mapPrescriptionsToIds(entity.getPrescriptions()))")
    SurveillanceSheetDTO toDto(SurveillanceSheet entity);

    @Mapping(source = "hospitalisationId", target = "hospitalisation")
    @Mapping(source = "miniConsultationId", target = "miniConsultation")
    @Mapping(target = "prescriptions", expression = "java(mapIdsToPrescriptions(dto.getPrescriptionIds()))")
    SurveillanceSheet toEntity(SurveillanceSheetDTO dto);

    default Set<Long> mapPrescriptionsToIds(Set<Prescription> prescriptions) {
        return prescriptions != null ? prescriptions.stream().map(Prescription::getId).collect(Collectors.toSet()) : null;
    }

    default Set<Prescription> mapIdsToPrescriptions(Set<Long> ids) {
        return ids != null
            ? ids
                .stream()
                .map(id -> {
                    Prescription p = new Prescription();
                    p.setId(id);
                    return p;
                })
                .collect(Collectors.toSet())
            : null;
    }

    default Hospitalisation fromHospitalisationId(Long id) {
        if (id == null) {
            return null;
        }
        Hospitalisation h = new Hospitalisation();
        h.setId(id);
        return h;
    }

    default MiniConsultation fromMiniConsultationId(Long id) {
        if (id == null) {
            return null;
        }
        MiniConsultation mc = new MiniConsultation();
        mc.setId(id);
        return mc;
    }
}
