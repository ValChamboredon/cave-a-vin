package fr.eni.cave.repository;

import fr.eni.cave.bo.client.Client;
import fr.eni.cave.bo.client.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PanierRepository extends JpaRepository<Panier, Integer> {

    //jpql
    @Query("SELECT p FROM Panier p WHERE p.client = :client AND p.numCommande IS NULL")
    List<Panier> findPaniersWithJPQL(@Param("client") Client client);

    //mots clés
    List<Panier> findByClientAndNumCommandeNull(Client client);

    //sql
    @Query(value = "SELECT * FROM CAV_SHOPPING_CART WHERE CLIENT_ID = :pseudo AND ORDER_NUMBER IS NOT NULL", nativeQuery = true)
    List<Panier> findCommandesWithSQL(@Param("pseudo") String pseudo);

    //mots clés
    List<Panier> findByClientAndNumCommandeNotNull(Client client);
}
