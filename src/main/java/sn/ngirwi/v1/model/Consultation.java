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
@Table(name="consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String patient;
    private String time;
    private String date;
    private String temperature;
    private String weight;
    private String tension;
    private String glycemie;
    private String comment;
    private String hypothesis;
    private String exams;
    private String treatment;
}
