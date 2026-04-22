package it.uniroma3.siw.siwtornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siwtornei.service.CommentoService;
import it.uniroma3.siw.siwtornei.service.PartitaService;
import it.uniroma3.siw.siwtornei.model.Commento;
import it.uniroma3.siw.siwtornei.model.Partita;

@Controller
public class CommentoController {

    @Autowired
    private CommentoService commentoService;
    
    @Autowired
    private PartitaService partitaService;

    // --- UTENTE REGISTRATO ---

    // Intercetta il form inviato dalla pagina della singola partita
    @PostMapping("/user/partita/{partitaId}/commento")
    public String salvaCommento(@PathVariable("partitaId") Long partitaId, @ModelAttribute("commento") Commento commento) {
    
        Partita partita = partitaService.findById(partitaId);
        
        commento.setPartita(partita);
        commentoService.saveCommento(commento);
    
        return "redirect:/partita/" + partitaId;
    }
}