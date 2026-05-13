package com.ecolemusique.repository;

import com.ecolemusique.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entite Instrument.
 * Spring Data JPA genere automatiquement les implementations.
 */
@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    /**
     * Filtrage par famille ET par disponibilite.
     */
    List<Instrument> findByFamilleAndDisponibilite(String famille, Boolean disponibilite);

    /**
     * Filtrage par famille uniquement.
     */
    List<Instrument> findByFamille(String famille);

    /**
     * Filtrage par disponibilite uniquement.
     */
    List<Instrument> findByDisponibilite(Boolean disponibilite);

    /**
     * Statistique : nombre d'instruments par famille.
     * Retourne une liste d'Object[] : [famille, count].
     */
    @Query("SELECT i.famille AS famille, COUNT(i) AS total FROM Instrument i GROUP BY i.famille ORDER BY COUNT(i) DESC")
    List<Object[]> countByFamille();

    /**
     * Nombre d'instruments disponibles.
     */
    long countByDisponibiliteTrue();
}
