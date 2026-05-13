package com.ecolemusique.controller;

import com.ecolemusique.entity.Cours;
import com.ecolemusique.service.CoursService;
import com.ecolemusique.service.ProfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller MVC pour la gestion des cours.
 */
@Controller
@RequestMapping("/cours")
@RequiredArgsConstructor
public class CoursController {

    private static final List<String> JOURS = List.of(
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
    );
    private static final List<String> NIVEAUX = List.of(
            "Debutant", "Intermediaire", "Avance", "Expert"
    );

    private final CoursService coursService;
    private final ProfService profService;

    @GetMapping
    public String list(@RequestParam(required = false) String niveau,
                       @RequestParam(required = false) String jour,
                       Model model) {
        model.addAttribute("cours", coursService.filter(niveau, jour));
        model.addAttribute("niveau", niveau);
        model.addAttribute("jour", jour);
        model.addAttribute("jours", JOURS);
        model.addAttribute("niveaux", NIVEAUX);
        return "cours/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("cours", new Cours());
        model.addAttribute("profs", profService.findAll());
        model.addAttribute("jours", JOURS);
        model.addAttribute("niveaux", NIVEAUX);
        model.addAttribute("isEdit", false);
        return "cours/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return coursService.findById(id)
                .map(c -> {
                    model.addAttribute("cours", c);
                    model.addAttribute("profs", profService.findAll());
                    model.addAttribute("jours", JOURS);
                    model.addAttribute("niveaux", NIVEAUX);
                    model.addAttribute("isEdit", true);
                    return "cours/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Cours introuvable.");
                    return "redirect:/cours";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("cours") Cours cours,
                       BindingResult result,
                       @RequestParam(value = "profId", required = false) Long profId,
                       Model model,
                       RedirectAttributes ra) {
        // Resoudre la relation Prof
        if (profId != null) {
            profService.findById(profId).ifPresent(cours::setProf);
        } else {
            cours.setProf(null);
        }

        if (result.hasErrors()) {
            model.addAttribute("profs", profService.findAll());
            model.addAttribute("jours", JOURS);
            model.addAttribute("niveaux", NIVEAUX);
            model.addAttribute("isEdit", cours.getId() != null);
            return "cours/form";
        }
        coursService.save(cours);
        ra.addFlashAttribute("success", "Cours planifie avec succes !");
        return "redirect:/cours";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        coursService.deleteById(id);
        ra.addFlashAttribute("success", "Cours supprime.");
        return "redirect:/cours";
    }
}
