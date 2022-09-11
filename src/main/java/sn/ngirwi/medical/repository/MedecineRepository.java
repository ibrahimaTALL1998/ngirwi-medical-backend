package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.Medecine;

/**
 * Spring Data JPA repository for the Medecine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedecineRepository extends JpaRepository<Medecine, Long> {}
