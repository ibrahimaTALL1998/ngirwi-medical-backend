package sn.ngirwi.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.ngirwi.v1.model.Consultation;

@Repository
public interface ConsulationRepository extends JpaRepository<Consultation, Long> {

    Consultation findByPatient(String patient);
}
