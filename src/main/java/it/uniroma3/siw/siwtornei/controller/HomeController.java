package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Intercetta la radice del sito (localhost:8080/)
    @GetMapping("/")
    public String index() {
        // Restituisce semplicemente il nome del file HTML principale
        return "index"; 
    }
}