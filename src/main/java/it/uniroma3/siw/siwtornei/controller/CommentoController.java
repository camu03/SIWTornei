package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import it.uniroma3.siw.siwtornei.service.CommentoService;
import it.uniroma3.siw.siwtornei.service.PartitaService;
import it.uniroma3.siw.siwtornei.service.UtenteService;
import it.uniroma3.siw.siwtornei.model.Commento;
import it.uniroma3.siw.siwtornei.model.Partita;
import it.uniroma3.siw.siwtornei.model.Utente;
import org.springframework.security.core.Authentication;
import java.security.Principal;

@Controller
public class CommentoController {

    private final CommentoService commentoService;
    private final PartitaService partitaService;
    private final UtenteService utenteService;

    public CommentoController(CommentoService commentoService, PartitaService partitaService, UtenteService utenteService) {
        this.commentoService = commentoService;
        this.partitaService = partitaService;
        this.utenteService = utenteService;
    }

    // --- UTENTE REGISTRATO ---

    // Intercetta il form inviato dalla pagina della singola partita
    @PostMapping("/user/partita/{partitaId}/commento")
    public String salvaCommento(@PathVariable("partitaId") Long partitaId, 
                                @ModelAttribute("commento") Commento commento,
                                Principal principal) { // Aggiungiamo Principal tra i parametri
    
        Partita partita = partitaService.findById(partitaId);

        commento.setPartita(partita);
        commento.setDataOra(LocalDateTime.now());

        // --- GESTIONE AUTORE ---
        if (principal != null) {
            // principal.getName() restituisce lo username usato per il login
            String username = principal.getName(); 
            Utente utenteLoggato = utenteService.findByUsername(username);
            commento.setUtente(utenteLoggato);
        }
        
        commentoService.saveCommento(commento);
    
        return "redirect:/partita/" + partitaId;
    }

    //elimina commento 
    @PostMapping("/user/commento/{id}/delete")
    public String eliminaCommento(@PathVariable("id") Long id, Authentication authentication) {
        
        Commento commento = commentoService.findById(id);
        String currentUsername = authentication.getName();
        
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")); // Verifica se ha il ruolo ADMIN
        
        // CONTROLLO SICUREZZA
        if (commento.getUtente().getUsername().equals(currentUsername) || isAdmin) {
            commentoService.deleteCommento(commento);
        }
        return "redirect:/partita/" + commento.getPartita().getId();
    }

    @GetMapping("/user/commento/{id}/edit")
    public String formModificaCommento(@PathVariable("id") Long id, Authentication authentication, Model model) {
        
        Commento commento = commentoService.findById(id);
        String currentUsername = authentication.getName();
        
        // CONTROLLO SICUREZZA: Solo ed esclusivamente l'autore può modificare
        if (!commento.getUtente().getUsername().equals(currentUsername)) {
            // Se un utente o un admin prova ad accedere, lo rispediamo alla partita
            return "redirect:/partita/" + commento.getPartita().getId();
        }
        
        model.addAttribute("commento", commento);
        return "commento-form"; 
    }
    
    // ==========================================
    // 3. SALVA LA MODIFICA DEL COMMENTO (POST)
    // ==========================================
    @PostMapping("/user/commento/{id}/edit")
    public String salvaModificaCommento(@PathVariable("id") Long id, 
                                        @RequestParam("testo") String nuovoTesto, 
                                        Authentication authentication) {
        
        Commento commento = commentoService.findById(id);
        String currentUsername = authentication.getName();
        
        // CONTROLLO SICUREZZA FINALE (prima di salvare sul database)
        if (commento.getUtente().getUsername().equals(currentUsername)) {
            commento.setTesto(nuovoTesto);
            
            commentoService.saveCommento(commento);
        }
        
        return "redirect:/partita/" + commento.getPartita().getId();
    }
}
