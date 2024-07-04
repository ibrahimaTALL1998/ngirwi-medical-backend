package sn.ngirwi.medical.service.dto;

import sn.ngirwi.medical.domain.BillElement;
import sn.ngirwi.medical.service.model.PrescriptionForm;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link sn.ngirwi.medical.domain.Bill} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BillDTO implements Serializable {

    private Long id;

    private Instant date;

    private String author;

    private PatientDTO patient;

    private Set<BillElement> elements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
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

    public Set<BillElement> getElements() {
        return elements;
    }

    public void setElements(Set<BillElement> elements) {
        this.elements = elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillDTO)) {
            return false;
        }

        BillDTO billDTO = (BillDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, billDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", author='" + getAuthor() + "'" +
            ", patient=" + getPatient() +
            "}";
    }
}
