package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.Hospitalisation;

/**
 * Spring Data JPA repository for the Hospitalisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HospitalisationRepository extends JpaRepository<Hospitalisation, Long> {}
