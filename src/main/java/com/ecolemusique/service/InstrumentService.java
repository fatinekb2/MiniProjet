package com.ecolemusique.service;

import com.ecolemusique.entity.Instrument;
import com.ecolemusique.repository.InstrumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service metier pour la gestion des instruments.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class InstrumentService {

    private final InstrumentRepository instrumentRepository;

    public List<Instrument> findAll() {
        return instrumentRepository.findAll();
    }

    public Optional<Instrument> findById(Long id) {
        return instrumentRepository.findById(id);
    }

    public Instrument save(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }

    public void deleteById(Long id) {
        instrumentRepository.deleteById(id);
    }

    /**
     * Bascule l'etat de disponibilite d'un instrument.
     */
    public Instrument toggleDisponibilite(Long id) {
        Instrument instrument = instrumentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instrument introuvable: " + id));
        instrument.setDisponibilite(!Boolean.TRUE.equals(instrument.getDisponibilite()));
        return instrumentRepository.save(instrument);
    }

    /**
     * Filtrage flexible : famille et/ou disponibilite (parametres optionnels).
     */
    @Transactional(readOnly = true)
    public List<Instrument> filter(String famille, Boolean disponibilite) {
        boolean hasFamille = famille != null && !famille.isBlank();
        boolean hasDispo = disponibilite != null;

        if (hasFamille && hasDispo) {
            return instrumentRepository.findByFamilleAndDisponibilite(famille, disponibilite);
        }
        if (hasFamille) {
            return instrumentRepository.findByFamille(famille);
        }
        if (hasDispo) {
            return instrumentRepository.findByDisponibilite(disponibilite);
        }
        return instrumentRepository.findAll();
    }

    /**
     * Popularite des instruments : nombre d'instruments par famille.
     */
    @Transactional(readOnly = true)
    public Map<String, Long> popularityByFamille() {
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : instrumentRepository.countByFamille()) {
            String famille = (String) row[0];
            Long total = ((Number) row[1]).longValue();
            map.put(famille, total);
        }
        return map;
    }

    public long countAll() {
        return instrumentRepository.count();
    }

    public long countDisponibles() {
        return instrumentRepository.countByDisponibiliteTrue();
    }
}
