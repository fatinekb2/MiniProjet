package com.ecolemusique.service;

import com.ecolemusique.entity.Prof;
import com.ecolemusique.repository.ProfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service metier pour la gestion des professeurs.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProfService {

    private final ProfRepository profRepository;

    public List<Prof> findAll() {
        return profRepository.findAll();
    }

    public Optional<Prof> findById(Long id) {
        return profRepository.findById(id);
    }

    public Prof save(Prof prof) {
        return profRepository.save(prof);
    }

    public void deleteById(Long id) {
        profRepository.deleteById(id);
    }

    public long countAll() {
        return profRepository.count();
    }
}
