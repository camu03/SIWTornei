package it.uniroma3.siw.siwtornei.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.repository.TorneoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.Partita;
import it.uniroma3.siw.siwtornei.model.StatoPartita;
import it.uniroma3.siw.siwtornei.repository.PartitaRepository;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.time.Year;

@Service
public class TorneoService{
    
    @Autowired 
    private TorneoRepository torneoRepository;
    @Autowired
    private PartitaRepository partitaRepository;


    @Transactional
    public void saveTorneo(Torneo torneo) {
        int annoCorrente = Year.now().getValue();
        if (torneo.getAnno() < annoCorrente) {
            throw new IllegalArgumentException("L'anno del torneo non può essere nel passato!");
        }
        torneoRepository.save(torneo);
    }

    @Transactional(readOnly = true)
    public List<Torneo> findAll() {
        return torneoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Torneo findById(Long id) {
        return torneoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Torneo findByNome(String nome) {
        return torneoRepository.findByNome(nome);
    }

    @Transactional(readOnly = true)
    public Map<Squadra, Integer> getClassifica(Torneo torneo) {
        Map<Squadra, Integer> mappa = new HashMap<>();
        for (Squadra s : torneo.getSquadre()) {
            mappa.put(s, 0);
        }

        List<Partita> partiteGiocate = partitaRepository.findByTorneoAndStato(torneo, StatoPartita.PLAYED);

        for(Partita p : partiteGiocate) {
            Squadra casa = p.getSquadraHome();
            Squadra ospite = p.getSquadraAway();
            if (p.getGolHome() != null && p.getGolAway() != null) {
                if(p.getGolHome()>p.getGolAway()){
                    mappa.put(casa, mappa.get(casa)+3);
                }else if(p.getGolHome()<p.getGolAway()){
                    mappa.put(ospite, mappa.get(ospite)+3);
                }else {
                    mappa.put(casa, mappa.get(casa)+1);
                    mappa.put(ospite, mappa.get(ospite)+1);
                }
            }
        }
        Comparator<Squadra> cmp = new Comparator<Squadra>(){
            @Override
            public int compare(Squadra s1, Squadra s2) {
                if(mappa.get(s1) > mappa.get(s2)) {
                    return -1;
                } else if(mappa.get(s1) < mappa.get(s2)) {
                    return 1;
                } else {
                    return s1.getNome().compareTo(s2.getNome());
                }
            }
        };

        List<Squadra> listaSquadre = new ArrayList<>(mappa.keySet());

        Collections.sort(listaSquadre, cmp); 

        Map<Squadra, Integer> classifica = new LinkedHashMap<>();
        for(Squadra s : listaSquadre) {
            classifica.put(s, mappa.get(s));
        }

        return classifica; 
    }
    
}
