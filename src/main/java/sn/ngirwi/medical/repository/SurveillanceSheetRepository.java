package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.SurveillanceSheet;

/**
 * Spring Data JPA repository for the SurveillanceSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveillanceSheetRepository extends JpaRepository<SurveillanceSheet, Long> {}
