package sn.ngirwi.v1.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Medicament {

    private String name;
    private String duration;
    private String frequency;
}
