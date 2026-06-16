package it.uniroma3.siw.siwtornei.controller;

import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.service.TorneoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController 
public class TorneoRestController {

    private final TorneoService torneoService;

    public TorneoRestController(TorneoService torneoService) {
        this.torneoService = torneoService;
    }

    @GetMapping("/api/tornei")
    public List<Map<String, Object>> getTorneiApi() {
        List<Torneo> tornei = torneoService.findAll();
 
        return tornei.stream().map(t -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", t.getId());
            map.put("nome", t.getNome());
            map.put("anno", t.getAnno());
            map.put("descrizione", t.getDescrizione());
            return map;
        }).collect(Collectors.toList());
    }

    @GetMapping("/api/tornei/{id}/squadre")
    public List<Map<String, Object>> getSquadreByTorneo(@PathVariable("id") Long id) {
        Torneo torneo = torneoService.findById(id);
        
        // Restituisce solo l'ID e il nome delle squadre iscritte a QUESTO torneo
        return torneo.getSquadre().stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", s.getId());
            map.put("nome", s.getNome());
            return map;
        }).collect(Collectors.toList());
    }
}