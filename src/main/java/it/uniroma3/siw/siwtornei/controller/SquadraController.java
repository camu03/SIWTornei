package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siwtornei.model.Giocatore;
import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.service.SquadraService;
import it.uniroma3.siw.siwtornei.service.TorneoService;
import org.springframework.ui.Model;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class SquadraController {
    
    private final SquadraService squadraService;
    private final TorneoService torneoService;

    public SquadraController(SquadraService squadraService, TorneoService torneoService) {
        this.squadraService = squadraService;
        this.torneoService = torneoService;
    }

    @GetMapping("/torneo/{id}/squadre")
    public String showSquadreTorneo(@PathVariable("id") Long id, Model model) {
        
        Torneo torneo = torneoService.findById(id);
        
        // Usiamo il Service corretto per estrarre i dati in modo sicuro!
        List<Squadra> squadre = squadraService.findByTorneo(torneo);
        
        model.addAttribute("torneo", torneo);
        model.addAttribute("squadre", squadre);
        
        return "squadre_torneo";
    }

    @GetMapping("/squadra/{id}")
    public String showDettagliSquadra(@PathVariable("id") Long id, Model model) {
        Squadra squadra = squadraService.findByIdWithGiocatori(id);
        if (squadra.getGiocatori() != null) {
            List<Giocatore> giocatoriOrdinati = new ArrayList<>(squadra.getGiocatori());
            giocatoriOrdinati.sort(Comparator.comparingInt(giocatore -> getRank(giocatore.getRuolo())));
            squadra.setGiocatori(giocatoriOrdinati);
        }
        model.addAttribute("squadra", squadra);
        return "squadra";
    }

    private int getRank(String ruolo) {
        if (ruolo == null) {
            return Integer.MAX_VALUE;
        }

        return switch (ruolo.trim().toUpperCase()) {
            case "PORTIERE" -> 0;
            case "DIFENSORE" -> 1;
            case "CENTROCAMPISTA" -> 2;
            case "ATTACCANTE" -> 3;
            default -> 4;
        };
    }

    // --- ADMIN (4.3) ---

    @GetMapping("/admin/squadra/new")
    public String formNuovaSquadra(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "admin/squadra-form";
    }

    @PostMapping("/admin/squadra")
    public String salvaSquadra(@ModelAttribute("squadra") Squadra squadra) {
        squadraService.saveSquadra(squadra);
        return "redirect:/squadre";
    }

    //modifica squadra 
    @GetMapping("/admin/squadra/{id}/edit")
    public String formModificaSquadra(@PathVariable("id") Long id, Model model) {
        Squadra squadra = squadraService.findById(id);
        model.addAttribute("squadra", squadra);
        return "admin/squadra-form";
    }

    @PostMapping("/admin/squadra/{id}")
    public String aggiornaSquadra(@PathVariable("id") Long id, @ModelAttribute("squadra") Squadra squadra) {
        squadra.setId(id); // Assicuriamoci di mantenere lo stesso ID
        squadraService.saveSquadra(squadra);
        return "redirect:/squadre";
    }

    //elimina squadra
    @PostMapping("/admin/squadra/{id}/delete")
    public String eliminaSquadra(@PathVariable("id") Long id) {
        squadraService.deleteSquadra(id);
        return "redirect:/squadre";
    }
}
