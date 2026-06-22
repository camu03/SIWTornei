package it.uniroma3.siw.siwtornei.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siw.siwtornei.service.PartitaService;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    private final PartitaService partitaService;

    public HomeController(PartitaService partitaService) {
        this.partitaService = partitaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("partiteOggi", partitaService.findPartiteDelGiorno());
        return "index";
    }

}