package it.uniroma3.siw.siwtornei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import it.uniroma3.siw.siwtornei.model.Arbitro;
import it.uniroma3.siw.siwtornei.service.ArbitroService;

@Controller
public class ArbitroController {
    
    @Autowired
    private ArbitroService arbitroService;

    @GetMapping("/arbitri")
    public String showArbitri(Model model) {
        model.addAttribute("arbitri", arbitroService.findAll());
        return "arbitri"; // Pagina con la lista di tutti gli arbitri
    }

    @GetMapping("/arbitro/{id}")
    public String showDettagliArbitro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("arbitro", arbitroService.findById(id));
        return "arbitro"; // Dettaglio del singolo arbitro
    }

    // --- ADMIN (Casi d'uso 4.3) ---

    @GetMapping("/admin/arbitro/new")
    public String formNuovoArbitro(Model model) {
        model.addAttribute("arbitro", new Arbitro());
        return "admin/formArbitro";
    }

    @PostMapping("/admin/arbitro")
    public String salvaArbitro(@ModelAttribute("arbitro") Arbitro arbitro) {
        arbitroService.saveArbitro(arbitro);
        return "redirect:/arbitri"; // Dopo il salvataggio, torna alla lista
    }
}