package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.siwtornei.service.TorneoService;
import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.Torneo;
import java.util.List;
import java.util.Map;

@Controller
public class TorneoController {

    private final TorneoService torneoService;

    public TorneoController(TorneoService torneoService) {
        this.torneoService = torneoService;
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

}