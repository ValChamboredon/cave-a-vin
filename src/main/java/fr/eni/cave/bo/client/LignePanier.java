package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CAV_LINE")
public class LignePanier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ID")
    private Integer id;

    @Column(name="QUANTITY")
    private int quantite;

    @Column(name="PRICE", precision = 2)
    private float prix;

}
