package sn.ngirwi.v1.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Ordonance {

    @Id
    private Long id;
    private String date;
    private String time;
    private Medicament[] medicaments;
}
