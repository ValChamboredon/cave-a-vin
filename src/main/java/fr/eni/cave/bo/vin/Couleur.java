package fr.eni.cave.bo.vin;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="CAV_COLOR")
public class Couleur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COLOR_ID")
    private Integer id;

    @Column(name="NAME",unique = true)
    private String nom;

}
