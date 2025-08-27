package sn.ngirwi.medical.domain;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "mini_consultation")
public class MiniConsultation extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "summary")
    private String summary;

    @Column(name = "diagnosis", length = 512)
    private String diagnosis;

    /**
     * Relation OneToOne :
     * Chaque fiche de surveillance peut avoir au plus une mini-consultation.
     */
    @OneToOne
    @JoinColumn(name = "surveillance_sheet_id", unique = true)
    private SurveillanceSheet surveillanceSheet;

    @Column(name = "prescription_id")
    private Long prescriptionId;

    public MiniConsultation() {}

    // Getters / setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public SurveillanceSheet getSurveillanceSheet() {
        return surveillanceSheet;
    }

    public void setSurveillanceSheet(SurveillanceSheet surveillanceSheet) {
        this.surveillanceSheet = surveillanceSheet;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
