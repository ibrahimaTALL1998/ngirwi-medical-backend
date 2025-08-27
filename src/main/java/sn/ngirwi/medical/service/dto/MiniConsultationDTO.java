package sn.ngirwi.medical.service.dto;

import java.io.Serializable;

public class MiniConsultationDTO implements Serializable {

    private Long id;

    private String summary;

    private String diagnosis;

    private Long prescriptionId;

    private Long surveillanceSheetId;

    // Getters and setters

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

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getSurveillanceSheetId() {
        return surveillanceSheetId;
    }

    public void setSurveillanceSheetId(Long surveillanceSheetId) {
        this.surveillanceSheetId = surveillanceSheetId;
    }

    @Override
    public String toString() {
        return (
            "MiniConsultationDTO{" +
            "id=" +
            id +
            ", summary='" +
            summary +
            '\'' +
            ", diagnosis='" +
            diagnosis +
            '\'' +
            ", prescriptionId=" +
            prescriptionId +
            ", surveillanceSheetId=" +
            surveillanceSheetId +
            '}'
        );
    }
}
