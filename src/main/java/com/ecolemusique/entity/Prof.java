package com.ecolemusique.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Entite representant un professeur de l'ecole de musique.
 */
@Entity
@Table(name = "profs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Prof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du professeur est obligatoire")
    @Column(nullable = false, length = 100)
    private String nom;

    @NotBlank(message = "La specialite du professeur est obligatoire")
    @Column(nullable = false, length = 100)
    private String specialite;
}
