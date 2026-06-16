package it.uniroma3.siw.siwtornei.controller;

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
    
    private final ArbitroService arbitroService;

    public ArbitroController(ArbitroService arbitroService) {
        this.arbitroService = arbitroService;
    }

    @GetMapping("/admin/arbitri")
    public String showArbitri(Model model) {
        model.addAttribute("arbitri", arbitroService.findAll());
        return "admin/arbitri"; // Pagina con la lista di tutti gli arbitri
    }

    @GetMapping("/admin/arbitro/{id}")
    public String showDettagliArbitro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("arbitro", arbitroService.findById(id));
        return "admin/arbitro"; // Dettaglio del singolo arbitro
    }

    // --- FUNZIONALITÀ ADMIN (Casi d'uso 4.3) ---

    @GetMapping("/admin/arbitro/new")
    public String formNuovoArbitro(Model model) {
        model.addAttribute("arbitro", new Arbitro());
        return "admin/arbitro-form"; 
    }

    @GetMapping("/admin/arbitro/modifica/{id}")
    public String formModificaArbitro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("arbitro", arbitroService.findById(id));
        return "admin/arbitro-form";
    }

    // UNICO METODO DI SALVATAGGIO (creazione e modifica)
    @PostMapping("/admin/arbitro/salva")
    public String salvaArbitro(@ModelAttribute("arbitro") Arbitro arbitro) {
        arbitroService.saveArbitro(arbitro);
        return "redirect:/admin/arbitri"; // Torna alla lista admin!
    }

    @PostMapping("/admin/arbitro/{id}/delete")
    public String eliminaArbitro(@PathVariable("id") Long id) {
        arbitroService.deleteArbitro(id);
        return "redirect:/admin/arbitri"; // Torna alla lista admin!
    }
}