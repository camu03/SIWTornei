package it.uniroma3.siw.siwtornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.siwtornei.service.TorneoService;
import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.Torneo;
import java.util.List;
import java.util.Map;

@Controller 
public class TorneoController {

    // Il Controller chiama il Service, MAI direttamente il Repository
    @Autowired
    private TorneoService torneoService;

    // @GetMapping intercetta le richieste verso l'indirizzo localhost:8080/tornei
    @GetMapping("/tornei")
    public String showTornei(Model model) {
        
        // 1. Chiediamo al Service di trovare tutti i tornei
        List<Torneo> elencoTornei = torneoService.findAll();
        
        // 2. Inseriamo la lista nel "pacco" Model, dandole un'etichetta ("tornei")
        model.addAttribute("tornei", elencoTornei);
        
        // 3. Restituiamo il NOME del file HTML (senza l'estensione) che mostrerà i dati
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
    @GetMapping("/admin/torneo/new")
    public String formNuovoTorneo(Model model) {
        // Passiamo alla pagina un oggetto Torneo vuoto per farlo riempire dall'utente
        model.addAttribute("torneo", new Torneo());
        return "admin/formNuovoTorneo"; 
    }

    // 2. Riceve i dati dal form e li salva nel database
    @PostMapping("/admin/torneo")
    public String salvaNuovoTorneo(@ModelAttribute("torneo") Torneo torneo) {
        // Il torneo ricevuto ha già i campi compilati (nome, indirizzo, ecc.) grazie a Thymeleaf
        torneoService.saveTorneo(torneo);
        
        // Dopo il salvataggio, reindirizziamo l'amministratore all'elenco di tutti i tornei
        return "redirect:/tornei";
    }

    // Mostra il modulo per MODIFICARE un torneo esistente
    @GetMapping("/admin/torneo/{id}/edit")
    public String formModificaTorneo(@PathVariable("id") Long id, Model model) {
        
        // 1. Recuperiamo il torneo dal database
        Torneo torneoEsistente = torneoService.findById(id);
        
        // 2. Lo mettiamo nel Model
        model.addAttribute("torneo", torneoEsistente);
        
        // 3. Restituiamo la pagina HTML. 
        // TRUCCO: Spesso si riutilizza lo stesso identico file HTML dell'inserimento!
        return "admin/formTorneo"; 
    }

    // Riceve i dati aggiornati dal form e li salva
    @PostMapping("/admin/torneo/{id}")
    public String aggiornaTorneo(@PathVariable("id") Long id, @ModelAttribute("torneo") Torneo torneoModificato) {
        
        // Per sicurezza, ci assicuriamo che il torneo modificato mantenga l'ID corretto
        torneoModificato.setId(id);
        
        // Il Service farà un UPDATE nel database perché l'ID è presente
        torneoService.saveTorneo(torneoModificato);
        
        // Dopo aver salvato, reindirizziamo l'utente alla pagina di dettaglio di questo torneo
        return "redirect:/torneo/" + id;
    }
    
}