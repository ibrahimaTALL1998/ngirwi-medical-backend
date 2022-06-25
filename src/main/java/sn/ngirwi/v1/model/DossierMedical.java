package sn.ngirwi.v1.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name="dossier_medical")
public class DossierMedical {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String motifConsultation;
    private String histoireMaladie;
    private String terrain;
    private String antecedantsPersonnels;
    private String antecedantsChirurgicaux;
    private String antecedantsFamiliaux;
    private String gynecoObstretrique;
    private String syndromique;
    private String dad;
    private String mom;
    private String siblings;
    private String descendants;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "patient_id", referencedColumnName = "matricule")
    //private Patient patient;
}
