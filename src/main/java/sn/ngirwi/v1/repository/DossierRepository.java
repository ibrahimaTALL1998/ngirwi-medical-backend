package sn.ngirwi.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.ngirwi.v1.model.DossierMedical;
import sn.ngirwi.v1.model.Patient;

@Repository
public interface DossierRepository extends JpaRepository<DossierMedical, Long> {

    //@Query("Select d from dossier_medical where d.matricule = :#{#patient.matricule}")
    //DossierMedical findByMatriculePatient(@Param("patient") Patient patient);
}
