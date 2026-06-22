package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import it.uniroma3.siw.siwtornei.model.Utente;
import it.uniroma3.siw.siwtornei.service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthenticationController {

    private final UtenteService utenteService;

    public AuthenticationController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

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
    public String registerUser(@ModelAttribute("utente") Utente utente, Model model) {

       
        if (utente.getRuolo() == null || utente.getRuolo().trim().isEmpty()) {
            model.addAttribute("erroreRuolo", "Campo obbligatorio: devi selezionare ADMIN o UTENTE per registrarti.");
            return "registrazione";
        }

        utenteService.registra(utente);

        return "redirect:/login";
    }
}
