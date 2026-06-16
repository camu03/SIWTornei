package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siwtornei.service.SquadraService;
import it.uniroma3.siw.siwtornei.service.TorneoService;
import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.Torneo;
import java.util.List;
import java.util.Map;

@Controller 
public class TorneoController {

    private final TorneoService torneoService;
    private final SquadraService squadraService;

    public TorneoController(TorneoService torneoService, SquadraService squadraService) {
        this.torneoService = torneoService;
        this.squadraService = squadraService;
    }

    // @GetMapping intercetta le richieste verso l'indirizzo localhost:8080/tornei
    @GetMapping("/tornei")
    public String showTornei(Model model) {
        
        List<Torneo> elencoTornei = torneoService.findAll();
        model.addAttribute("tornei", elencoTornei);

        return "tornei"; 
    }

    @GetMapping("/torneo/{id}")
    public String showDettagliTorneo(@PathVariable("id") Long id, Model model) {
        Torneo torneo = torneoService.findById(id);
        model.addAttribute("torneo", torneo);
        return "torneo";
    }

    // Questo va nel TorneoController!
    @GetMapping("/torneo/{id}/classifica")
    public String showClassifica(@PathVariable("id") Long id, Model model) {
        Torneo torneo = torneoService.findById(id);
        
        // Chiamiamo il famoso metodo che calcola i punti
        Map<Squadra, Integer> classifica = torneoService.getClassifica(torneo);
        
        model.addAttribute("torneo", torneo);
        model.addAttribute("classifica", classifica);
        
        return "classifica";
    }

    // --- FUNZIONALITÀ AMMINISTRATORE ---

    // 1. Mostra il modulo (form) per creare un nuovo torneo
    @GetMapping("/admin/tornei/nuovo")
    public String formNuovoTorneo(Model model) {
        model.addAttribute("torneo", new Torneo());
        
        model.addAttribute("tutteLeSquadre", squadraService.findAll()); 
        return "admin/torneo-form";
    }

    // 2. Mostra il modulo per MODIFICARE un torneo esistente
    @GetMapping("/admin/tornei/modifica/{id}")
    public String formModificaTorneo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("torneo", torneoService.findById(id));
        
        model.addAttribute("tutteLeSquadre", squadraService.findAll()); 
        return "admin/torneo-form";
    }

    // 3. UNICO METODO che riceve i dati dal form e li salva (sia nuovo che modifica)
    @PostMapping("/admin/tornei/salva")
    public String salvaTorneo(@ModelAttribute("torneo") Torneo torneo) {
        torneoService.saveTorneo(torneo);
        
        return "redirect:/admin/tornei";
    }

    
    
}