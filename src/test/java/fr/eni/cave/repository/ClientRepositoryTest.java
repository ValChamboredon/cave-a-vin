package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Client;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @DisplayName("Crée un client, save et vérif qu'il existe en base")
    public void test_createClient_save_checkIfExists(){

        Client client = Client
                .builder()
                .pseudo("pseudo")
                .password("password")
                .nom("nom")
                .prenom("prenom")
                .build();
        log.info("-------------------------");
        log.info("Create Client :{}", client);
        log.info("-------------------------");
        clientRepository.save(client);

        assertTrue(clientRepository.existsById(client.getPseudo()));
    }

    @Test
    @DisplayName("Crée un client, delete le et vérif qu'il n'existe plus en base")
    public void test_createClient_save_delete_checkIfDoesntExists(){

        Client client = Client
                .builder()
                .pseudo("pseudo2")
                .password("password2")
                .nom("nom2")
                .prenom("prenom2")
                .build();
        log.info("-------------------------");
        log.info("Create Client :{}", client);
        log.info("-------------------------");
        clientRepository.save(client);
        log.info("-------------------------");
        log.info("Delete Client :{}", client);
        log.info("-------------------------");
        clientRepository.deleteById(client.getPseudo());

        assertFalse(clientRepository.existsById(client.getPseudo()));
    }

    @Test
    @DisplayName("Vérifie que deux clients avec le même pseudo sont considérés comme égaux")
    public void test_clientEquality_basedOnPseudoOnly() {
        Client c1 = Client.builder().pseudo("unique").nom("NomA").build();
        Client c2 = Client.builder().pseudo("unique").nom("NomB").build();

        assertEquals(c1, c2);
    }

}