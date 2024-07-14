package sn.ngirwi.medical.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sn.ngirwi.medical.domain.enumeration.HospitalisationStatus;

/**
 * A Hospitalisation.
 */
@Entity
@Table(name = "hospitalisation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Hospitalisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "entry_date")
    private Instant entryDate;

    @Column(name = "release_date")
    private Instant releaseDate;

    @Column(name = "doctor_name")
    private String doctorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private HospitalisationStatus status;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossierMedical", "consultations" }, allowSetters = true)
    private Patient patient;

    @OneToMany(mappedBy = "hospitalisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "hospitalisation" }, allowSetters = true)
    private Set<SurveillanceSheet> surveillanceSheets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hospitalisation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEntryDate() {
        return this.entryDate;
    }

    public Hospitalisation entryDate(Instant entryDate) {
        this.setEntryDate(entryDate);
        return this;
    }

    public void setEntryDate(Instant entryDate) {
        this.entryDate = entryDate;
    }

    public Instant getReleaseDate() {
        return this.releaseDate;
    }

    public Hospitalisation releaseDate(Instant releaseDate) {
        this.setReleaseDate(releaseDate);
        return this;
    }

    public void setReleaseDate(Instant releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public Hospitalisation doctorName(String doctorName) {
        this.setDoctorName(doctorName);
        return this;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public HospitalisationStatus getStatus() {
        return this.status;
    }

    public Hospitalisation status(HospitalisationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(HospitalisationStatus status) {
        this.status = status;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Hospitalisation patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Set<SurveillanceSheet> getSurveillanceSheets() {
        return this.surveillanceSheets;
    }

    public void setSurveillanceSheets(Set<SurveillanceSheet> surveillanceSheets) {
        if (this.surveillanceSheets != null) {
            this.surveillanceSheets.forEach(i -> i.setHospitalisation(null));
        }
        if (surveillanceSheets != null) {
            surveillanceSheets.forEach(i -> i.setHospitalisation(this));
        }
        this.surveillanceSheets = surveillanceSheets;
    }

    public Hospitalisation surveillanceSheets(Set<SurveillanceSheet> surveillanceSheets) {
        this.setSurveillanceSheets(surveillanceSheets);
        return this;
    }

    public Hospitalisation addSurveillanceSheet(SurveillanceSheet surveillanceSheet) {
        this.surveillanceSheets.add(surveillanceSheet);
        surveillanceSheet.setHospitalisation(this);
        return this;
    }

    public Hospitalisation removeSurveillanceSheet(SurveillanceSheet surveillanceSheet) {
        this.surveillanceSheets.remove(surveillanceSheet);
        surveillanceSheet.setHospitalisation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospitalisation)) {
            return false;
        }
        return id != null && id.equals(((Hospitalisation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hospitalisation{" +
            "id=" + getId() +
            ", entryDate='" + getEntryDate() + "'" +
            ", releaseDate='" + getReleaseDate() + "'" +
            ", doctorName='" + getDoctorName() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
