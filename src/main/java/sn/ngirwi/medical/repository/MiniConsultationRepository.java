package sn.ngirwi.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ngirwi.medical.domain.MiniConsultation;

@Repository
public interface MiniConsultationRepository extends JpaRepository<MiniConsultation, Long> {}
