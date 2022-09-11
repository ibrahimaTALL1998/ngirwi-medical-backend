package sn.ngirwi.medical.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Consultation.
 */
@Entity
@Table(name = "consultation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_time")
    private Instant dateTime;

    @NotNull
    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Double weight;

    @NotNull
    @Column(name = "tension", nullable = false)
    private String tension;

    @Column(name = "glycemie")
    private String glycemie;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "hypothesis", nullable = false)
    private String hypothesis;

    @NotNull
    @Column(name = "exams", nullable = false)
    private String exams;

    @NotNull
    @Column(name = "treatment", nullable = false)
    private String treatment;

    @Column(name = "author")
    private String author;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossierMedical", "consultations" }, allowSetters = true)
    private Patient patient;

    @JsonIgnoreProperties(value = { "consultation", "medecines" }, allowSetters = true)
    @OneToOne(mappedBy = "consultation")
    private Prescription ordonance;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Consultation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTime() {
        return this.dateTime;
    }

    public Consultation dateTime(Instant dateTime) {
        this.setDateTime(dateTime);
        return this;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public Double getTemperature() {
        return this.temperature;
    }

    public Consultation temperature(Double temperature) {
        this.setTemperature(temperature);
        return this;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWeight() {
        return this.weight;
    }

    public Consultation weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getTension() {
        return this.tension;
    }

    public Consultation tension(String tension) {
        this.setTension(tension);
        return this;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public String getGlycemie() {
        return this.glycemie;
    }

    public Consultation glycemie(String glycemie) {
        this.setGlycemie(glycemie);
        return this;
    }

    public void setGlycemie(String glycemie) {
        this.glycemie = glycemie;
    }

    public String getComment() {
        return this.comment;
    }

    public Consultation comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHypothesis() {
        return this.hypothesis;
    }

    public Consultation hypothesis(String hypothesis) {
        this.setHypothesis(hypothesis);
        return this;
    }

    public void setHypothesis(String hypothesis) {
        this.hypothesis = hypothesis;
    }

    public String getExams() {
        return this.exams;
    }

    public Consultation exams(String exams) {
        this.setExams(exams);
        return this;
    }

    public void setExams(String exams) {
        this.exams = exams;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public Consultation treatment(String treatment) {
        this.setTreatment(treatment);
        return this;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getAuthor() {
        return this.author;
    }

    public Consultation author(String author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Consultation patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Prescription getOrdonance() {
        return this.ordonance;
    }

    public void setOrdonance(Prescription prescription) {
        if (this.ordonance != null) {
            this.ordonance.setConsultation(null);
        }
        if (prescription != null) {
            prescription.setConsultation(this);
        }
        this.ordonance = prescription;
    }

    public Consultation ordonance(Prescription prescription) {
        this.setOrdonance(prescription);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consultation)) {
            return false;
        }
        return id != null && id.equals(((Consultation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consultation{" +
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
            "}";
    }
}
