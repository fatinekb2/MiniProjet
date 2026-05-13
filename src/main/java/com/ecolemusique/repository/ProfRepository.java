package com.ecolemusique.repository;

import com.ecolemusique.entity.Prof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entite Prof.
 */
@Repository
public interface ProfRepository extends JpaRepository<Prof, Long> {

    List<Prof> findBySpecialite(String specialite);
}
