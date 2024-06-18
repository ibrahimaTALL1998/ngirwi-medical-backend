package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.Hospitalisation;
import sn.ngirwi.medical.domain.Patient;
import sn.ngirwi.medical.domain.enumeration.HospitalisationStatus;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Hospitalisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HospitalisationRepository extends JpaRepository<Hospitalisation, Long> {
    Optional<Hospitalisation> findByPatient_Id(Long id);

    Optional<Hospitalisation> findFirstByPatient_IdAndStatus(Long id, HospitalisationStatus status);

    // Optional<Hospitalisation> findByPatient_IdAndStatus(Long id, HospitalisationStatus status);


}
