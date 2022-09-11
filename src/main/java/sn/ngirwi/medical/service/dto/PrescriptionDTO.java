package sn.ngirwi.medical.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link sn.ngirwi.medical.domain.Prescription} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PrescriptionDTO implements Serializable {

    private Long id;

    private Instant creationDate;

    private String author;

    private ConsultationDTO consultation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ConsultationDTO getConsultation() {
        return consultation;
    }

    public void setConsultation(ConsultationDTO consultation) {
        this.consultation = consultation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrescriptionDTO)) {
            return false;
        }

        PrescriptionDTO prescriptionDTO = (PrescriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prescriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PrescriptionDTO{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", author='" + getAuthor() + "'" +
            ", consultation=" + getConsultation() +
            "}";
    }
}
