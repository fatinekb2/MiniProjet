package com.ecolemusique.config;

import com.ecolemusique.entity.Cours;
import com.ecolemusique.entity.Instrument;
import com.ecolemusique.entity.Prof;
import com.ecolemusique.repository.CoursRepository;
import com.ecolemusique.repository.InstrumentRepository;
import com.ecolemusique.repository.ProfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;

/**
 * Initialise la base avec des donnees de demonstration au demarrage.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final InstrumentRepository instrumentRepository;
    private final ProfRepository profRepository;
    private final CoursRepository coursRepository;

    @Override
    public void run(String... args) {
        if (instrumentRepository.count() > 0) {
            return; // deja initialise
        }

        // ===== Instruments =====
        instrumentRepository.save(Instrument.builder().nom("Violon").famille("Cordes").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Violoncelle").famille("Cordes").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Guitare classique").famille("Cordes").disponibilite(false).build());
        instrumentRepository.save(Instrument.builder().nom("Harpe").famille("Cordes").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Flute traversiere").famille("Vents").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Clarinette").famille("Vents").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Saxophone").famille("Vents").disponibilite(false).build());
        instrumentRepository.save(Instrument.builder().nom("Trompette").famille("Vents").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Batterie").famille("Percussions").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Xylophone").famille("Percussions").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Djembe").famille("Percussions").disponibilite(false).build());
        instrumentRepository.save(Instrument.builder().nom("Piano").famille("Claviers").disponibilite(true).build());
        instrumentRepository.save(Instrument.builder().nom("Synthetiseur").famille("Claviers").disponibilite(true).build());

        // ===== Professeurs =====
        Prof alice = profRepository.save(Prof.builder().nom("Alice Dupont").specialite("Piano").build());
        Prof bruno = profRepository.save(Prof.builder().nom("Bruno Martin").specialite("Guitare").build());
        Prof clara = profRepository.save(Prof.builder().nom("Clara Bernard").specialite("Violon").build());
        Prof david = profRepository.save(Prof.builder().nom("David Lefevre").specialite("Batterie").build());
        Prof elise = profRepository.save(Prof.builder().nom("Elise Moreau").specialite("Chant").build());

        // ===== Cours =====
        coursRepository.save(Cours.builder().jour("Lundi").heureDebut(LocalTime.of(17, 0)).dureeMin(60).niveau("Debutant").prof(alice).build());
        coursRepository.save(Cours.builder().jour("Lundi").heureDebut(LocalTime.of(18, 30)).dureeMin(90).niveau("Avance").prof(clara).build());
        coursRepository.save(Cours.builder().jour("Mardi").heureDebut(LocalTime.of(16, 0)).dureeMin(45).niveau("Debutant").prof(bruno).build());
        coursRepository.save(Cours.builder().jour("Mercredi").heureDebut(LocalTime.of(10, 0)).dureeMin(60).niveau("Intermediaire").prof(elise).build());
        coursRepository.save(Cours.builder().jour("Mercredi").heureDebut(LocalTime.of(14, 0)).dureeMin(60).niveau("Debutant").prof(david).build());
        coursRepository.save(Cours.builder().jour("Mercredi").heureDebut(LocalTime.of(16, 0)).dureeMin(90).niveau("Avance").prof(alice).build());
        coursRepository.save(Cours.builder().jour("Jeudi").heureDebut(LocalTime.of(18, 0)).dureeMin(60).niveau("Intermediaire").prof(bruno).build());
        coursRepository.save(Cours.builder().jour("Vendredi").heureDebut(LocalTime.of(17, 30)).dureeMin(90).niveau("Expert").prof(clara).build());
        coursRepository.save(Cours.builder().jour("Samedi").heureDebut(LocalTime.of(9, 0)).dureeMin(60).niveau("Debutant").prof(elise).build());
        coursRepository.save(Cours.builder().jour("Samedi").heureDebut(LocalTime.of(11, 0)).dureeMin(60).niveau("Intermediaire").prof(david).build());
    }
}
