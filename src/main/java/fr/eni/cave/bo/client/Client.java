package fr.eni.cave.bo.client;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CAV_CLIENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = {"pseudo"})
@Builder
public class Client {

    @Id
    @Column(name = "LOGIN", nullable = false)
    private String pseudo;

    @ToString.Exclude
    @Column(name = "PASSWORD", length = 68)
    private String password;

    @Column(name = "LAST_NAME", length = 90)
    private String nom;

    @Column(name = "FIRST_NAME", length = 150)
    private String prenom;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "ADRESS_ID")
    private Adresse adresse;

}
