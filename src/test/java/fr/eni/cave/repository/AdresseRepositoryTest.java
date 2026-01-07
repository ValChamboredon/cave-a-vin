package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Adresse;
import fr.eni.cave.bo.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Slf4j
class AdresseRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Test de sauvegarde")
    public void test_save(){

        Adresse adresse = Adresse
                .builder()
                .rue("rue")
                .codePostal("44000")
                .ville("ville")
                .build();

        Client client = Client
                .builder()
                .pseudo("pseudo")
                .password("password")
                .nom("nom")
                .prenom("prenom")
                .adresse(adresse)
                .build();

        client = clientRepository.save(client);
        clientRepository.flush();

        log.info("Create Client :{}", client);

        assertFalse(clientRepository.findAll().isEmpty());
    }


    @Test
    @DisplayName("Test de suppression")
    public void test_delete(){

        Adresse adresse = Adresse
                .builder()
                .rue("rue")
                .codePostal("44000")
                .ville("ville")
                .build();

        Client client = Client
                .builder()
                .pseudo("pseudo")
                .password("password")
                .nom("nom")
                .prenom("prenom")
                .adresse(adresse)
                .build();

        client = clientRepository.save(client);
        clientRepository.flush();
        log.info("Create Client :{}", client);

        clientRepository.delete(client);
        clientRepository.flush();
        log.info("Delete Client :{}", client);



        assertEquals(0, clientRepository.findAll().size());
    }



    @Test
    @DisplayName("Test d'entités détachées supprimées")
    public void test_entity_detachees_deleted() {

        Adresse adresse = Adresse.builder()
                .rue("15 rue de Paris").codePostal("35000").ville("Rennes")
                .build();
        Client client = Client.builder()
                .pseudo("bobeponge@email.fr").nom("Eponge").prenom("Bob")
                .adresse(adresse)
                .build();

        client = clientRepository.save(client);
        clientRepository.flush();

        entityManager.clear();

        log.info("Client détaché (avant suppression) : {}", client);

        clientRepository.deleteById(client.getPseudo());
        clientRepository.flush();

        assertFalse(clientRepository.existsById("bobeponge@email.fr"));
    }


}