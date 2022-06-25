package sn.ngirwi.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ngirwi.v1.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}