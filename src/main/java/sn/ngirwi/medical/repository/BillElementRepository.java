package sn.ngirwi.medical.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.BillElement;

/**
 * Spring Data JPA repository for the BillElement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillElementRepository extends JpaRepository<BillElement, Long> {
    List<BillElement> findByBill_Id(Long id);

    boolean existsByNameAndPriceAndPercentageAndQuantityAndBill_Id(String name, Double price, Double percentage, Integer quantity, Long id);

    long deleteByNameAndPriceAndPercentageAndQuantityAndBill_Id(String name, Double price, Double percentage, Integer quantity, Long id);
}
