package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.BillElement;

/**
 * Spring Data JPA repository for the BillElement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillElementRepository extends JpaRepository<BillElement, Long> {}
