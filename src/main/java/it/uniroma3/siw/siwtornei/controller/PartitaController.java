package it.uniroma3.siw.siwtornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import it.uniroma3.siw.siwtornei.model.Partita;
import it.uniroma3.siw.siwtornei.model.StatoPartita;
import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.service.PartitaService;
import it.uniroma3.siw.siwtornei.service.TorneoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.siwtornei.service.ArbitroService;
import java.util.List;
import org.springframework.ui.Model;

@Controller
public class PartitaController {
    
    @Autowired
    private PartitaService partitaService;
    @Autowired
    private TorneoService torneoService;
    @Autowired
    private ArbitroService arbitroService; 

    //ordina partite per data
    @GetMapping("/torneo/{id}/partite")
    public String showPartitePerTorneo(@PathVariable("id") Long id, Model model, 
                                        @RequestParam(required = false, defaultValue = "false") boolean sortByDateAsc,
                                        @RequestParam(required = false, defaultValue = "false") boolean sortByDateDesc) {

    Torneo torneo = torneoService.findById(id);
    List<Partita> partiteDelTorneo = partitaService.findByTorneo(torneo);
    
    model.addAttribute("torneo", torneo);
    model.addAttribute("partite", partiteDelTorneo);
    if(sortByDateAsc){
        model.addAttribute("partite", partitaService.findByTorneoOrderByDataOraAsc(torneo));
    }
    if(sortByDateDesc){
        model.addAttribute("partite", partitaService.findByTorneoOrderByDataOraDesc(torneo));
    }

    return "partite_torneo";

    }

    @GetMapping("/partita/{id}")
    public String showDettagliPartita(@PathVariable("id") Long id, Model model) {
        Partita partita = partitaService.findById(id);
        model.addAttribute("partita", partita);
        return "partita";
    }


    @GetMapping("/admin/torneo/{torneoId}/partita/new")
    public String formNuovaPartita(@PathVariable("torneoId") Long torneoId, Model model) {
        Torneo torneo = torneoService.findById(torneoId);
        model.addAttribute("partita", new Partita());
        model.addAttribute("torneo", torneo);
        model.addAttribute("squadre", torneo.getSquadre());
        
        // NUOVO: Passa la lista di tutti gli arbitri al form
        model.addAttribute("arbitri", arbitroService.findAll()); 
        
        return "admin/formNuovaPartita"; 
    }

    @GetMapping("/admin/partita/{id}/edit")
    public String formModificaPartita(@PathVariable("id") Long id, Model model) {
        Partita partita = partitaService.findById(id);
        model.addAttribute("partita", partita);
        
        // NUOVO: Passa la lista di tutti gli arbitri al form per farli scegliere/cambiare
        model.addAttribute("arbitri", arbitroService.findAll()); 
        
        // OPZIONALE MA CONSIGLIATO: Passa anche le squadre del torneo in caso volessi cambiarle
        model.addAttribute("squadre", partita.getTorneo().getSquadre());
        
        return "admin/formModificaPartita"; 
    }

    @PostMapping("/admin/torneo/{torneoId}/partita")
    public String salvaNuovaPartita(@PathVariable("torneoId") Long torneoId, @ModelAttribute("partita") Partita partita) {
        
        // 1. Recuperiamo il torneo dal DB
        Torneo torneo = torneoService.findById(torneoId);
        
        // 2. Leghiamo la nuova partita al suo torneo! (Fondamentale)
        partita.setTorneo(torneo);
        
        // 3. Impostiamo lo stato di default: una nuova partita deve ancora essere giocata
        partita.setStato(StatoPartita.SCHEDULED);
        
        // 4. Salviamo nel database tramite il Service
        partitaService.savePartita(partita);
        
        // 5. Reindirizziamo l'admin al calendario di questo torneo per fargli vedere la nuova partita!
        return "redirect:/torneo/" + torneoId + "/partite";
    }

    // L'annotazione @ModelAttribute prende i dati digitati nel form e li trasforma automaticamente in un oggetto Partita
    @PostMapping("/admin/partita/{id}")
    public String updatePartita(@PathVariable("id") Long id, @ModelAttribute("partita") Partita partitaAggiornata) {
        
        // 1. Salviamo la partita aggiornata tramite il Service
        partitaService.savePartita(partitaAggiornata);
        
        // 2. Redirect: invece di mostrare una pagina statica, reindirizziamo l'utente 
        // alla pagina di dettaglio della partita appena modificata!
        return "redirect:/partita/" + id;
    }
    
    //elimina partita
    @PostMapping("/admin/partita/{id}/delete")
    public String eliminaPartita(@PathVariable("id") Long id) {
        Partita partita = partitaService.findById(id);
        Long torneoId = partita.getTorneo().getId(); // Per reindirizzare al calendario del torneo
        partitaService.deletePartita(id);
        return "redirect:/torneo/" + torneoId + "/partite";
    }
}
