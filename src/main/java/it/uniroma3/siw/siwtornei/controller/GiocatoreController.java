package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.siwtornei.service.GiocatoreService;
import it.uniroma3.siw.siwtornei.service.SquadraService;
import it.uniroma3.siw.siwtornei.model.Giocatore;
import it.uniroma3.siw.siwtornei.model.Squadra;

@Controller
public class GiocatoreController {

    private final GiocatoreService giocatoreService;
    private final SquadraService squadraService;

    public GiocatoreController(GiocatoreService giocatoreService, SquadraService squadraService) {
        this.giocatoreService = giocatoreService;
        this.squadraService = squadraService;
    }
    
    @GetMapping("/giocatore/{id}")
    public String showDettaglioGiocatore(@PathVariable("id") Long id, Model model) {
        Giocatore giocatore = giocatoreService.findById(id);
        model.addAttribute("giocatore", giocatore);
        return "giocatore"; // La pagina del giocatore
    } 

    // --- ADMIN (4.3) ---

    @GetMapping("/admin/squadra/{squadraId}/giocatore/new")
    public String formNuovoGiocatore(@PathVariable("squadraId") Long squadraId, Model model) {
        Squadra squadra = squadraService.findById(squadraId);
        model.addAttribute("squadra", squadra);
        model.addAttribute("giocatore", new Giocatore());
        return "admin/giocatore-form";
    }

    @PostMapping("/admin/squadra/{squadraId}/giocatore")
    public String salvaGiocatore(@PathVariable("squadraId") Long squadraId, @ModelAttribute("giocatore") Giocatore giocatore) {
        Squadra squadra = squadraService.findById(squadraId);
        giocatore.setSquadra(squadra); // Leghiamo il giocatore alla sua squadra
        giocatoreService.saveGiocatore(giocatore);
        
        return "redirect:/squadra/" + squadraId; 
    }

    //modifica giocatore
    @GetMapping("/admin/giocatore/{id}/edit")
    public String formModificaGiocatore(@PathVariable("id") Long id, Model model) {
        Giocatore giocatore = giocatoreService.findById(id);
        model.addAttribute("giocatore", giocatore);
        return "admin/giocatore-form"; 
    }

    @PostMapping("/admin/giocatore/{id}")
    public String aggiornaGiocatore(@PathVariable("id") Long id, @ModelAttribute("giocatore") Giocatore giocatore) {
        giocatore.setId(id); // Assicuriamoci di mantenere lo stesso ID
        giocatoreService.saveGiocatore(giocatore);
        return "redirect:/giocatori";
    }

}
