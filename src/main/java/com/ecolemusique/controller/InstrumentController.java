package com.ecolemusique.controller;

import com.ecolemusique.entity.Instrument;
import com.ecolemusique.service.InstrumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller MVC pour la gestion des instruments.
 */
@Controller
@RequestMapping("/instruments")
@RequiredArgsConstructor
public class InstrumentController {

    private final InstrumentService instrumentService;

    /**
     * Liste des instruments avec filtrage optionnel (famille + disponibilite).
     */
    @GetMapping
    public String list(@RequestParam(required = false) String famille,
                       @RequestParam(required = false) Boolean disponibilite,
                       Model model) {
        model.addAttribute("instruments", instrumentService.filter(famille, disponibilite));
        model.addAttribute("famille", famille);
        model.addAttribute("disponibilite", disponibilite);
        return "instruments/list";
    }

    /**
     * Formulaire de creation.
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("instrument", new Instrument());
        model.addAttribute("isEdit", false);
        return "instruments/form";
    }

    /**
     * Formulaire de modification.
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return instrumentService.findById(id)
                .map(i -> {
                    model.addAttribute("instrument", i);
                    model.addAttribute("isEdit", true);
                    return "instruments/form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Instrument introuvable.");
                    return "redirect:/instruments";
                });
    }

    /**
     * Enregistrement (creation ou mise a jour).
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("instrument") Instrument instrument,
                       BindingResult result,
                       Model model,
                       RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", instrument.getId() != null);
            return "instruments/form";
        }
        if (instrument.getDisponibilite() == null) {
            instrument.setDisponibilite(Boolean.TRUE);
        }
        instrumentService.save(instrument);
        ra.addFlashAttribute("success", "Instrument enregistre avec succes !");
        return "redirect:/instruments";
    }

    /**
     * Suppression.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        instrumentService.deleteById(id);
        ra.addFlashAttribute("success", "Instrument supprime.");
        return "redirect:/instruments";
    }

    /**
     * Bascule rapide de la disponibilite.
     */
    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, RedirectAttributes ra) {
        instrumentService.toggleDisponibilite(id);
        ra.addFlashAttribute("success", "Disponibilite mise a jour.");
        return "redirect:/instruments";
    }
}
