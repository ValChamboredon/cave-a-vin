package fr.eni.cave;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import fr.eni.cave.bo.client.LignePanier;
import fr.eni.cave.bo.client.Panier;
import fr.eni.cave.repository.PanierRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestOneToManyUni {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	PanierRepository panierRepository;

    @Test
    @DisplayName("test_save_nouvelleLigne_nouveauPanier")
    public void test_save_nouvelleLigne_nouveauPanier() {
        LignePanier lignePanier = LignePanier.builder()
                .quantite(4)
                .prix(95.8f)
                .build();

        Panier panier = Panier.builder()
                .numCommande(null)
                .paye(false)
                .build();

        panier.getLignesPanier().add(lignePanier);
        panier.setPrixTotal(lignePanier.getPrix());

        panier = panierRepository.save(panier);
        entityManager.flush();

        log.info("Le panier créé : {}", panier);

        assertThat(panier.getId()).isNotNull();
        assertThat(panier.getLignesPanier()).hasSize(1);
        assertThat(panier.getPrixTotal()).isEqualTo(95.8f);
    }

    @Test
    @DisplayName("test_save_nouvelleLigne_Panier")
    public void test_save_nouvelleLigne_Panier() {

        Panier panier = panierEnDB();
        log.info("Initialiement : {}", panier);

        LignePanier nouvelleLigne = LignePanier.builder()
                .quantite(10)
                .prix(239.5f)
                .build();

        panier.getLignesPanier().add(nouvelleLigne);

        panier.setPrixTotal(panier.getPrixTotal() + nouvelleLigne.getPrix());

        panier = panierRepository.save(panier);
        entityManager.flush();

        log.info("Après l'ajout de la nouvelle ligne : {}", panier);

        assertThat(panier.getLignesPanier()).hasSize(2);
        assertThat(panier.getPrixTotal()).isEqualTo(273.85f);
    }

    @Test
    @DisplayName("test_delete")
    public void test_delete() {
        Panier panier = panierEnDB();
        Integer idLigne = panier.getLignesPanier().getFirst().getId();

        panierRepository.delete(panier);
        panierRepository.flush();

        assertThat(panierRepository.findById(panier.getId())).isEmpty();
        assertNull(entityManager.find(LignePanier.class, idLigne));
    }

    @Test
    @DisplayName("test_orphanRemoval")
    public void test_orphanRemoval() {

        Panier panier = panierEnDB();
        Integer idLigne = panier.getLignesPanier().getFirst().getId();

        panier.getLignesPanier().removeFirst();
        panier.setPrixTotal(0);

        panierRepository.save(panier);
        panierRepository.flush();

        LignePanier ligneEnDB = entityManager.find(LignePanier.class, idLigne);
        assertNull(ligneEnDB);
    }



	private Panier panierEnDB() {
		final Panier panier = new Panier();
		final LignePanier lp = LignePanier
				.builder()
				.quantite(3)
				.prix(3 * 11.45f)
				.build();
		panier.getLignesPanier().add(lp);
		panier.setPrixTotal(lp.getPrix());

		entityManager.persist(panier);
		entityManager.flush();

		assertThat(panier.getId()).isGreaterThan(0);
		assertThat(panier.getId()).isGreaterThan(0);

		return panier;
	}
}
