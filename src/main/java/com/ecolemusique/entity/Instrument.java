package com.ecolemusique.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Entite representant un instrument de musique de l'ecole.
 */
@Entity
@Table(name = "instruments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'instrument est obligatoire")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "La famille de l'instrument est obligatoire")
    @Column(nullable = false, length = 50)
    private String famille;

    @NotNull(message = "La disponibilite est obligatoire")
    @Column(nullable = false)
    @Builder.Default
    private Boolean disponibilite = Boolean.TRUE;
}
