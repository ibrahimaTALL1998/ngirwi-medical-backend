package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.BillElement;

import java.util.List;

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
