package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import it.uniroma3.siw.siwtornei.service.TorneoService;
import it.uniroma3.siw.siwtornei.model.Giocatore;
import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.StatoPartita;
//import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.model.Partita;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.siwtornei.service.GiocatoreService;
import it.uniroma3.siw.siwtornei.service.PartitaService;
import it.uniroma3.siw.siwtornei.service.SquadraService;
import it.uniroma3.siw.siwtornei.service.ArbitroService;

@Controller
@RequestMapping("/admin") 
public class AdminController {

    private final TorneoService torneoService;
    private final SquadraService squadraService;
    private final GiocatoreService giocatoreService;
    private final PartitaService partitaService;
    private final ArbitroService arbitroService;

    public AdminController(TorneoService torneoService, SquadraService squadraService, GiocatoreService giocatoreService, PartitaService partitaService, ArbitroService arbitroService) {
        this.torneoService = torneoService;
        this.squadraService = squadraService;
        this.giocatoreService = giocatoreService;
        this.partitaService = partitaService;
        this.arbitroService = arbitroService;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    // --- GESTIONE TORNEI ---

    // 1. Mostra l'elenco dei tornei lato admin
    @GetMapping("/tornei")
    public String manageTornei(Model model) {
        model.addAttribute("tornei", torneoService.findAll());
        return "admin/tornei";
    }

    // --- GESTIONE SQUADRE ---

    // 1. Mostra l'elenco delle squadre
    @GetMapping("/squadre")
    public String manageSquadre(Model model) {
        model.addAttribute("squadre", squadraService.findAll());
        return "admin/squadre";
    }

    // 2. Form per nuova squadra
    @GetMapping("/squadre/nuova")
    public String newSquadraForm(Model model) {
        model.addAttribute("squadra", new Squadra());
        return "admin/squadra-form";
    }

    // 3. Form per modificare una squadra esistente
    @GetMapping("/squadre/modifica/{id}")
    public String editSquadraForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("squadra", squadraService.findById(id)); 
        return "admin/squadra-form";
    }

    // 4. Salva o aggiorna la squadra
    @PostMapping("/squadre/salva")
    public String saveSquadra(@ModelAttribute("squadra") Squadra squadra) {
        squadraService.saveSquadra(squadra);
        return "redirect:/admin/squadre";
    }

    @PostMapping("/squadre/{id}/delete")
    public String deleteSquadra(@PathVariable("id") Long id) {
        squadraService.deleteSquadra(id);
        return "redirect:/admin/squadre";
    }

    // --- GESTIONE GIOCATORI ---

    // 1. Mostra l'elenco dei giocatori
    @GetMapping("/giocatori")
    public String manageGiocatori(Model model) {
        model.addAttribute("giocatori", giocatoreService.findAll());
        return "admin/giocatori";
    }

    // 2. Form per nuovo giocatore
    @GetMapping("/giocatori/nuovo")
    public String newGiocatoreForm(Model model) {
        model.addAttribute("giocatore", new Giocatore());
        model.addAttribute("squadre", squadraService.findAll()); 
        return "admin/giocatore-form";
    }

    // 3. Form per modificare un giocatore
    @GetMapping("/giocatori/modifica/{id}")
    public String editGiocatoreForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("giocatore", giocatoreService.findById(id)); 
        model.addAttribute("squadre", squadraService.findAll());
        return "admin/giocatore-form";
    }

    // 4. Salva o aggiorna
    @PostMapping("/giocatori/salva")
    public String saveGiocatore(@ModelAttribute("giocatore") Giocatore giocatore) {
        giocatoreService.saveGiocatore(giocatore);
        return "redirect:/admin/giocatori";
    }

    @PostMapping("/giocatori/{id}/delete")
    public String deleteGiocatore(@PathVariable("id") Long id) {
        giocatoreService.deleteGiocatore(id);
        return "redirect:/admin/giocatori";
    }

    // --- GESTIONE PARTITE ---

    // 1. Mostra l'elenco delle partite
    @GetMapping("/partite")
    public String managePartite(Model model) {
        model.addAttribute("partite", partitaService.findAll());
        return "admin/partite";
    }

    // 2. Mostra il form per creare una nuova partita
    @GetMapping("/partite/nuova")
    public String newPartitaForm(Model model) {
        model.addAttribute("partita", new Partita());
        
        // Passiamo tutte le liste necessarie per i menu a tendina
        model.addAttribute("tornei", torneoService.findAll()); 
        model.addAttribute("squadre", squadraService.findAll()); 
        model.addAttribute("arbitri", arbitroService.findAll()); 
        model.addAttribute("stati", StatoPartita.values()); 
        
        return "admin/partita-form";
    }

    // 3. Mostra il form pre-compilato per la modifica
    @GetMapping("/partite/modifica/{id}")
    public String editPartitaForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("partita", partitaService.findById(id)); 
        
        // Anche qui dobbiamo passare le liste per i menu a tendina!
        model.addAttribute("tornei", torneoService.findAll()); 
        model.addAttribute("squadre", squadraService.findAll()); 
        model.addAttribute("arbitri", arbitroService.findAll()); 
        model.addAttribute("stati", StatoPartita.values()); 
        
        return "admin/partita-form";
    }

    // 4. Salva la partita nel database (vale sia per nuova che per modifica)
    @PostMapping("/partite/salva")
    public String savePartita(@ModelAttribute("partita") Partita partita) {
        partitaService.savePartita(partita);
        return "redirect:/admin/partite";
    }

    @PostMapping("/partite/{id}/delete")
    public String deletePartita(@PathVariable("id") Long id) {
        partitaService.deletePartita(id);
        return "redirect:/admin/partite";
    }

}    