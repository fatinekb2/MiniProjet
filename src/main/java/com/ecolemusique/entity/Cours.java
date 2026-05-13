package com.ecolemusique.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

/**
 * Entite representant un cours planifie.
 * Relation @ManyToOne vers Prof (plusieurs cours peuvent etre donnes par un meme professeur).
 */
@Entity
@Table(name = "cours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le jour du cours est obligatoire")
    @Column(nullable = false, length = 20)
    private String jour;

    @NotNull(message = "L'heure de debut est obligatoire")
    @DateTimeFormat(pattern = "HH:mm")
    @Column(nullable = false)
    private LocalTime heureDebut;

    @NotNull(message = "La duree (en minutes) est obligatoire")
    @Positive(message = "La duree doit etre strictement positive")
    @Column(nullable = false)
    private Integer dureeMin;

    @NotBlank(message = "Le niveau est obligatoire")
    @Column(nullable = false, length = 50)
    private String niveau;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "prof_id")
    @ToString.Exclude
    private Prof prof;
}
