package fr.eni.cave.bo.vin;


import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @ToString @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name ="CAV_BOTTLE")
public class Bouteille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOTTLE_ID")
    private Integer id;

    @Column(name="NAME", unique = true, length = 250)
    private String nom;

    @Column(name="SPARKLING")
    private boolean petillant;

    @Column(name="VINTAGE", length = 100)
    private String millesime;

    @Column(name="QUANTITY")
    private int quantite;

    @Column(name="PRICE")
    private float prix;

    @ManyToOne
    @JoinColumn(name="REGION_ID")
    private Region region;

    @ManyToOne
    @JoinColumn(name="COLOR_ID")
    private Couleur couleur;
}
