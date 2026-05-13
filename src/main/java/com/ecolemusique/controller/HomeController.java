package com.ecolemusique.controller;

import com.ecolemusique.service.CoursService;
import com.ecolemusique.service.InstrumentService;
import com.ecolemusique.service.ProfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller du Dashboard - page d'accueil.
 * Affiche les statistiques globales de l'ecole.
 */
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final InstrumentService instrumentService;
    private final ProfService profService;
    private final CoursService coursService;

    @GetMapping("/")
    public String dashboard(Model model) {
        // Statistiques globales
        model.addAttribute("totalInstruments", instrumentService.countAll());
        model.addAttribute("totalInstrumentsDispo", instrumentService.countDisponibles());
        model.addAttribute("totalProfs", profService.countAll());
        model.addAttribute("coursParSemaine", coursService.countCoursParSemaine());

        // Popularite des instruments (nombre par famille)
        model.addAttribute("popularite", instrumentService.popularityByFamille());

        // Repartition des cours par jour
        model.addAttribute("coursParJour", coursService.coursParJour());

        return "index";
    }
}
