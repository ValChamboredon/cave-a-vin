package fr.eni.cave.bo.client;

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
@Table(name ="CAV_ADDRESS")
public class Adresse {

    @Id
    @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private Integer id;

    @Column(name = "STREET", length = 250)
    private String rue;

    @Column(name = "POSTAL_CODE", length = 5)
    private String codePostal;

    @Column(name = "CITY", length = 150)
    private String ville;
}
