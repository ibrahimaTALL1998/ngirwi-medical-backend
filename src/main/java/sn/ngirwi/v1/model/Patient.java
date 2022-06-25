package sn.ngirwi.v1.model;

import lombok.*;
import sn.ngirwi.v1.enumeration.Gender;
import sn.ngirwi.v1.enumeration.MaritalStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //private final String matricule = this.name + '_' + this.surname + '_' + this.birthday;
    private String name;
    private String surname;
    private String birthday;
    private Gender gender;
    private String cni; //it's the cni now
    private String phone;
    private String job;
    private MaritalStatus maritalStatus;
    private String address;
    private String dateAjout = String.valueOf(LocalDate.now());
}
