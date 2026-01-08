package fr.eni.cave.association;

import fr.eni.cave.bo.Proprio;
import fr.eni.cave.bo.Utilisateur;
import fr.eni.cave.bo.client.Client;
import fr.eni.cave.repository.ClientRepository;
import fr.eni.cave.repository.ProprioRepository;
import fr.eni.cave.repository.UtilisateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
public class TestHeritage {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Autowired
	ProprioRepository proprioRepository;

	@Autowired
	ClientRepository clientRepository;

	@BeforeEach
	public void initDB() {
		List<Utilisateur> utilisateurs = new ArrayList<>();
		utilisateurs.add(Utilisateur
				.builder()
				.pseudo("harrisonford@email.fr")
				.password("IndianaJones3")
				.nom("Ford")
				.prenom("Harrison")
				.build());

		utilisateurs.add(Proprio
				.builder()
				.pseudo("georgelucas@email.fr")
				.password("Réalisateur&Producteur")
				.nom("Lucas")
				.prenom("George")
				.siret("12345678901234")
				.build());

		utilisateurs.add(Client
				.builder()
				.pseudo("natalieportman@email.fr")
				.password("MarsAttacks!")
				.nom("Portman")
				.prenom("Natalie")
				.build());

		// Contexte de la DB
		utilisateurs.forEach(e -> {
			entityManager.persist(e);
		});
	}

    @Test
    @DisplayName("Test_findAll_Utilisateur : doit retourner les 3 utilisateurs")
    public void test_findAll_Utilisateur() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();

        log.info("Les utilisateurs :");
        utilisateurs.forEach(u -> log.info("{}", u));

        assertThat(utilisateurs).hasSize(3);
    }

    @Test
    @DisplayName("Test_findAll_Proprio : doit retourner uniquement le propriétaire")
    public void test_findAll_Proprio() {
        List<Proprio> proprios = proprioRepository.findAll();

        log.info("Les propriétaires :");
        proprios.forEach(p -> log.info("{}", p));

        assertThat(proprios).hasSize(1);
        assertThat(proprios.getFirst().getPseudo()).isEqualTo("georgelucas@email.fr");
        assertThat(proprios.getFirst().getSiret()).isEqualTo("12345678901234");
    }

    @Test
    @DisplayName("Test_findAll_Client : doit retourner uniquement le client")
    public void test_findAll_Client() {
        List<Client> clients = clientRepository.findAll();

        log.info("Les clients :");
        clients.forEach(c -> log.info("{}", c));

        assertThat(clients).hasSize(1);
        assertThat(clients.getFirst().getPseudo()).isEqualTo("natalieportman@email.fr");
    }
}
