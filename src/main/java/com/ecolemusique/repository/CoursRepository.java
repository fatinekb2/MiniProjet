package com.ecolemusique.repository;

import com.ecolemusique.entity.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entite Cours.
 */
@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    /**
     * Filtrage par niveau ET par jour.
     */
    List<Cours> findByNiveauAndJour(String niveau, String jour);

    /**
     * Filtrage par niveau uniquement.
     */
    List<Cours> findByNiveau(String niveau);

    /**
     * Filtrage par jour uniquement.
     */
    List<Cours> findByJour(String jour);

    /**
     * Statistique : nombre de cours par jour.
     */
    @Query("SELECT c.jour AS jour, COUNT(c) AS total FROM Cours c GROUP BY c.jour")
    List<Object[]> countByJour();

    /**
     * Cours rattaches a un professeur donne.
     */
    long countByProfId(Long profId);
}
