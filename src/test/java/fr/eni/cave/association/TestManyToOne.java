package fr.eni.cave.association;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import fr.eni.cave.bo.vin.Bouteille;
import fr.eni.cave.bo.vin.Couleur;
import fr.eni.cave.bo.vin.Region;
import fr.eni.cave.repository.BouteilleRepository;
import fr.eni.cave.repository.CouleurRepository;
import fr.eni.cave.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
public class TestManyToOne {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private BouteilleRepository bouteilleRepository;
    @Autowired
    private CouleurRepository couleurRepository;
    @Autowired
    private RegionRepository regionRepository;

    private Couleur rouge, blanc, rose;
    private Region grandEst, paysDeLaLoire, nouvelleAquitaine;

    @BeforeEach
    public void initDB() {
        rouge = couleurRepository.save(Couleur.builder().nom("Rouge").build());
        blanc = couleurRepository.save(Couleur.builder().nom("Blanc").build());
        rose = couleurRepository.save(Couleur.builder().nom("Rosé").build());

        grandEst = regionRepository.save(Region.builder().nom("Grand Est").build());
        paysDeLaLoire = regionRepository.save(Region.builder().nom("Pays de la Loire").build());
        nouvelleAquitaine = regionRepository.save(Region.builder().nom("Nouvelle Aquitaine").build());

        entityManager.flush();
    }

    @Test
    @DisplayName("test_save")
    public void test_save() {
        Bouteille bouteille = Bouteille.builder()
                .nom("Blanc du DOMAINE ENI Ecole")
                .petillant(false).millesime("2022").quantite(1298).prix(23.95f)
                .region(paysDeLaLoire).couleur(blanc)
                .build();

        bouteille = bouteilleRepository.save(bouteille);
        log.info("Bouteille saved : {}", bouteille);
        assertThat(bouteille.getId()).isNotNull();
    }

    @Test
    @DisplayName("test_save_bouteilles_regions_couleurs")
    public void test_save_bouteilles_regions_couleurs() {
        List<Bouteille> bouteilles = jeuDeDonnees();
        bouteilleRepository.saveAll(bouteilles);
        entityManager.flush();

        log.info("L'ensemble des bouteilles enregistrées du test test_save_bouteilles_regions_couleurs() :");
        bouteilleRepository.findAll().forEach(b -> log.info("{}", b));

        assertThat(bouteilleRepository.count()).isEqualTo(5);
    }

    @Test
    @DisplayName("test_delete")
    public void test_delete() {
        Bouteille bouteille = Bouteille.builder()
                .nom("Test Delete").region(grandEst).couleur(rouge).build();
        bouteille = bouteilleRepository.save(bouteille);

        Integer bottleId = bouteille.getId();
        bouteilleRepository.deleteById(bottleId);
        entityManager.flush();

        assertNull(entityManager.find(Bouteille.class, bottleId));
        assertThat(couleurRepository.existsById(rouge.getId())).isTrue();
        assertThat(regionRepository.existsById(grandEst.getId())).isTrue();
    }

    private List<Bouteille> jeuDeDonnees() {
        List<Bouteille> bouteilles = new ArrayList<>();
        bouteilles.add(Bouteille
                .builder()
                .nom("Blanc du DOMAINE ENI Ecole")
                .millesime("2022")
                .prix(23.95f)
                .quantite(1298)
                .region(paysDeLaLoire)
                .couleur(blanc)
                .build());
        bouteilles.add(Bouteille
                .builder()
                .nom("Rouge du DOMAINE ENI Ecole")
                .millesime("2018")
                .prix(11.45f)
                .quantite(987)
                .region(paysDeLaLoire)
                .couleur(rouge)
                .build());
        bouteilles.add(Bouteille
                .builder()
                .nom("Blanc du DOMAINE ENI Service")
                .millesime("2022")
                .prix(34)
                .petillant(true)
                .quantite(111)
                .region(grandEst)
                .couleur(blanc)
                .build());
        bouteilles.add(Bouteille
                .builder()
                .nom("Rouge du DOMAINE ENI Service")
                .millesime("2012")
                .prix(8.15f)
                .quantite(344)
                .region(paysDeLaLoire)
                .couleur(rouge)
                .build());
        bouteilles.add(Bouteille
                .builder()
                .nom("Rosé du DOMAINE ENI")
                .millesime("2020")
                .prix(33)
                .quantite(1987)
                .region(nouvelleAquitaine)
                .couleur(rose)
                .build());
        return bouteilles;
    }
}
