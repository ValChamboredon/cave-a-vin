package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"id"})
@Builder
@Entity
@Table(name="CAV_SHOPPING_CART")
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHOPPING_CART_ID")
    private Integer id;

    @Column(name = "ORDER_NUMBER")
    private String numCommande;

    @Column(name = "TOTAL_PRICE", precision = 2)
    private float prixTotal;

    @Column(name = "PAID")
    private boolean paye;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "SHOPPING_CART_ID")
    private List<LignePanier> lignesPanier = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
