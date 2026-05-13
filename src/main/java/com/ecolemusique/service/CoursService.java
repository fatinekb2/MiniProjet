package com.ecolemusique.service;

import com.ecolemusique.entity.Cours;
import com.ecolemusique.repository.CoursRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service metier pour la gestion des cours.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CoursService {

    private final CoursRepository coursRepository;

    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

    public Optional<Cours> findById(Long id) {
        return coursRepository.findById(id);
    }

    public Cours save(Cours cours) {
        return coursRepository.save(cours);
    }

    public void deleteById(Long id) {
        coursRepository.deleteById(id);
    }

    /**
     * Filtrage flexible des cours par niveau et/ou jour.
     */
    @Transactional(readOnly = true)
    public List<Cours> filter(String niveau, String jour) {
        boolean hasNiveau = niveau != null && !niveau.isBlank();
        boolean hasJour = jour != null && !jour.isBlank();

        if (hasNiveau && hasJour) {
            return coursRepository.findByNiveauAndJour(niveau, jour);
        }
        if (hasNiveau) {
            return coursRepository.findByNiveau(niveau);
        }
        if (hasJour) {
            return coursRepository.findByJour(jour);
        }
        return coursRepository.findAll();
    }

    /**
     * Nombre total de cours par semaine.
     * (un cours dans le planning hebdomadaire = un cours par semaine)
     */
    @Transactional(readOnly = true)
    public long countCoursParSemaine() {
        return coursRepository.count();
    }

    /**
     * Repartition des cours par jour de la semaine (ordre logique).
     */
    @Transactional(readOnly = true)
    public Map<String, Long> coursParJour() {
        // Ordre canonique des jours
        List<String> ordre = List.of("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
        Map<String, Long> brut = new LinkedHashMap<>();
        for (Object[] row : coursRepository.countByJour()) {
            String j = (String) row[0];
            Long total = ((Number) row[1]).longValue();
            brut.put(j, total);
        }
        Map<String, Long> ordonne = new LinkedHashMap<>();
        for (String j : ordre) {
            ordonne.put(j, brut.getOrDefault(j, 0L));
        }
        // Ajoute les jours non standards eventuels
        brut.forEach((k, v) -> ordonne.putIfAbsent(k, v));
        return ordonne;
    }
}
