package sn.ngirwi.medical.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link sn.ngirwi.medical.domain.Consultation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ConsultationDTO implements Serializable {

    private Long id;

    private Instant dateTime;

    @NotNull
    private Double temperature;

    @NotNull
    private Double weight;

    @NotNull
    private String tension;

    private String glycemie;

    private String comment;

    @NotNull
    private String hypothesis;

    @NotNull
    private String exams;

    @NotNull
    private String treatment;

    private String author;

    private PatientDTO patient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getTension() {
        return tension;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getGlycemie() {
        return glycemie;
    }

    public void setGlycemie(String glycemie) {
        this.glycemie = glycemie;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHypothesis() {
        return hypothesis;
    }

    public void setHypothesis(String hypothesis) {
        this.hypothesis = hypothesis;
    }

    public String getExams() {
        return exams;
    }

    public void setExams(String exams) {
        this.exams = exams;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultationDTO)) {
            return false;
        }

        ConsultationDTO consultationDTO = (ConsultationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, consultationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultationDTO{" +
            "id=" + getId() +
            ", dateTime='" + getDateTime() + "'" +
            ", temperature=" + getTemperature() +
            ", weight=" + getWeight() +
            ", tension='" + getTension() + "'" +
            ", glycemie='" + getGlycemie() + "'" +
            ", comment='" + getComment() + "'" +
            ", hypothesis='" + getHypothesis() + "'" +
            ", exams='" + getExams() + "'" +
            ", treatment='" + getTreatment() + "'" +
            ", author='" + getAuthor() + "'" +
            ", patient=" + getPatient() +
            "}";
    }
}
