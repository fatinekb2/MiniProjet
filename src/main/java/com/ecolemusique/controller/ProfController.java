package com.ecolemusique.controller;

import com.ecolemusique.entity.Prof;
import com.ecolemusique.service.ProfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller MVC pour la gestion des professeurs.
 */
@Controller
@RequestMapping("/profs")
@RequiredArgsConstructor
public class ProfController {

    private final ProfService profService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("profs", profService.findAll());
        return "profs/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("prof", new Prof());
        model.addAttribute("isEdit", false);
        return "profs/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return profService.findById(id)
                .map(p -> {
                    model.addAttribute("prof", p);
                    model.addAttribute("isEdit", true);
                    return "profs/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Professeur introuvable.");
                    return "redirect:/profs";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("prof") Prof prof,
                       BindingResult result,
                       Model model,
                       RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", prof.getId() != null);
            return "profs/form";
        }
        profService.save(prof);
        ra.addFlashAttribute("success", "Professeur enregistre avec succes !");
        return "redirect:/profs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        profService.deleteById(id);
        ra.addFlashAttribute("success", "Professeur supprime.");
        return "redirect:/profs";
    }
}
