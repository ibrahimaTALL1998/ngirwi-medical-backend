package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.Prescription;

/**
 * Spring Data JPA repository for the Prescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {}
