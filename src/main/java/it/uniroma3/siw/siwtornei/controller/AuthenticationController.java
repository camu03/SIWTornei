package it.uniroma3.siw.siwtornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import it.uniroma3.siw.siwtornei.model.Utente;
import it.uniroma3.siw.siwtornei.repository.UtenteRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthenticationController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        if (referrer != null && !referrer.contains("/login")) {
            request.getSession().setAttribute("url_prior_login", referrer);
        }
        return "login";
    }

    @GetMapping("/registrazione")
    public String showRegistrationForm(Model model) {
        model.addAttribute("utente", new Utente());
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String registerUser(@ModelAttribute("utente") Utente utente, org.springframework.ui.Model model) {
        
        // Se il ruolo è nullo o vuoto, blocchiamo il salvataggio!
        if (utente.getRuolo() == null || utente.getRuolo().trim().isEmpty()) {
            // Aggiungiamo un messaggio di errore da mostrare nella pagina
            model.addAttribute("erroreRuolo", "Campo obbligatorio: devi selezionare ADMIN o UTENTE per registrarti.");
            // Rimandiamo l'utente alla pagina di registrazione senza fare danni
            return "registrazione"; 
        }
        
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        
        // Salviamo l'utente
        utenteRepository.save(utente);
        
        // Reindirizziamo al login
        return "redirect:/login";
    }
}