package sn.ngirwi.medical.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SurveillanceSheet.
 */
@Entity
@Table(name = "surveillance_sheet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SurveillanceSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ta")
    private String ta;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "pulse_rate")
    private String pulseRate;

    @Column(name = "respiratory_frequency")
    private String respiratoryFrequency;

    @Column(name = "recoloration_time")
    private String recolorationTime;

    @Column(name = "glasgow")
    private String glasgow;

    @Column(name = "gravity_class")
    private String gravityClass;

    @Column(name = "horary_diuresis")
    private String horaryDiuresis;

    @Column(name = "spo_2")
    private String spo2;

    @Column(name = "treatment")
    private String treatment;

    @Column(name = "health_evolution")
    private String healthEvolution;

    @Column(name = "sheet_date")
    private String sheetDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "patient" }, allowSetters = true)
    private Hospitalisation hospitalisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SurveillanceSheet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTa() {
        return this.ta;
    }

    public SurveillanceSheet ta(String ta) {
        this.setTa(ta);
        return this;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public SurveillanceSheet temperature(String temperature) {
        this.setTemperature(temperature);
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPulseRate() {
        return this.pulseRate;
    }

    public SurveillanceSheet pulseRate(String pulseRate) {
        this.setPulseRate(pulseRate);
        return this;
    }

    public void setPulseRate(String pulseRate) {
        this.pulseRate = pulseRate;
    }

    public String getRespiratoryFrequency() {
        return this.respiratoryFrequency;
    }

    public SurveillanceSheet respiratoryFrequency(String respiratoryFrequency) {
        this.setRespiratoryFrequency(respiratoryFrequency);
        return this;
    }

    public void setRespiratoryFrequency(String respiratoryFrequency) {
        this.respiratoryFrequency = respiratoryFrequency;
    }

    public String getRecolorationTime() {
        return this.recolorationTime;
    }

    public SurveillanceSheet recolorationTime(String recolorationTime) {
        this.setRecolorationTime(recolorationTime);
        return this;
    }

    public void setRecolorationTime(String recolorationTime) {
        this.recolorationTime = recolorationTime;
    }

    public String getGlasgow() {
        return this.glasgow;
    }

    public SurveillanceSheet glasgow(String glasgow) {
        this.setGlasgow(glasgow);
        return this;
    }

    public void setGlasgow(String glasgow) {
        this.glasgow = glasgow;
    }

    public String getGravityClass() {
        return this.gravityClass;
    }

    public SurveillanceSheet gravityClass(String gravityClass) {
        this.setGravityClass(gravityClass);
        return this;
    }

    public void setGravityClass(String gravityClass) {
        this.gravityClass = gravityClass;
    }

    public String getHoraryDiuresis() {
        return this.horaryDiuresis;
    }

    public SurveillanceSheet horaryDiuresis(String horaryDiuresis) {
        this.setHoraryDiuresis(horaryDiuresis);
        return this;
    }

    public void setHoraryDiuresis(String horaryDiuresis) {
        this.horaryDiuresis = horaryDiuresis;
    }

    public String getSpo2() {
        return this.spo2;
    }

    public SurveillanceSheet spo2(String spo2) {
        this.setSpo2(spo2);
        return this;
    }

    public void setSpo2(String spo2) {
        this.spo2 = spo2;
    }

    public String getTreatment() {
        return this.treatment;
    }

    public SurveillanceSheet treatment(String treatment) {
        this.setTreatment(treatment);
        return this;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getHealthEvolution() {
        return this.healthEvolution;
    }

    public SurveillanceSheet healthEvolution(String healthEvolution) {
        this.setHealthEvolution(healthEvolution);
        return this;
    }

    public void setHealthEvolution(String healthEvolution) {
        this.healthEvolution = healthEvolution;
    }

    public String getSheetDate() {
        return this.sheetDate;
    }

    public SurveillanceSheet sheetDate(String sheetDate) {
        this.setSheetDate(sheetDate);
        return this;
    }

    public void setSheetDate(String sheetDate) {
        this.sheetDate = sheetDate;
    }

    public Hospitalisation getHospitalisation() {
        return this.hospitalisation;
    }

    public void setHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisation = hospitalisation;
    }

    public SurveillanceSheet hospitalisation(Hospitalisation hospitalisation) {
        this.setHospitalisation(hospitalisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SurveillanceSheet)) {
            return false;
        }
        return id != null && id.equals(((SurveillanceSheet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SurveillanceSheet{" +
            "id=" + getId() +
            ", ta='" + getTa() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", pulseRate='" + getPulseRate() + "'" +
            ", respiratoryFrequency='" + getRespiratoryFrequency() + "'" +
            ", recolorationTime='" + getRecolorationTime() + "'" +
            ", glasgow='" + getGlasgow() + "'" +
            ", gravityClass='" + getGravityClass() + "'" +
            ", horaryDiuresis='" + getHoraryDiuresis() + "'" +
            ", spo2='" + getSpo2() + "'" +
            ", treatment='" + getTreatment() + "'" +
            ", healthEvolution='" + getHealthEvolution() + "'" +
            ", sheetDate='" + getSheetDate() + "'" +
            "}";
    }
}
