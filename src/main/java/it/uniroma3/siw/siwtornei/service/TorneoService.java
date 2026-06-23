package it.uniroma3.siw.siwtornei.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.repository.TorneoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import java.util.List;
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
import java.util.stream.Collectors;

@Service
public class TorneoService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TorneoService.class);

    private final TorneoRepository torneoRepository;
    private final PartitaRepository partitaRepository;

    public TorneoService(TorneoRepository torneoRepository, PartitaRepository partitaRepository) {
        this.torneoRepository = torneoRepository;
        this.partitaRepository = partitaRepository;
    }


    @Transactional
    public void saveTorneo(Torneo torneo) {
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
        torneo = torneoRepository.findById(torneo.getId()).orElse(null);
        Map<Squadra, Integer> mappa = new HashMap<>();

        if (torneo.getSquadre() != null) {
            for (Squadra s : torneo.getSquadre()) {
                mappa.put(s, 0);
            }
        }

        List<Partita> partiteGiocate = partitaRepository.findByTorneoAndStato(torneo, StatoPartita.PLAYED);

        for(Partita p : partiteGiocate) {
            Squadra casa = p.getSquadraHome();
            Squadra ospite = p.getSquadraAway();
            
            if (casa != null) mappa.putIfAbsent(casa, 0);
            if (ospite != null) mappa.putIfAbsent(ospite, 0);

            
            if (p.getGolHome() != null && p.getGolAway() != null && casa != null && ospite != null) {
                if(p.getGolHome() > p.getGolAway()){
                    mappa.put(casa, mappa.get(casa) + 3);
                } else if(p.getGolHome() < p.getGolAway()){
                    mappa.put(ospite, mappa.get(ospite) + 3);
                } else {
                    mappa.put(casa, mappa.get(casa) + 1);
                    mappa.put(ospite, mappa.get(ospite) + 1);
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
                    
                    String nome1 = s1.getNome() != null ? s1.getNome() : "";
                    String nome2 = s2.getNome() != null ? s2.getNome() : "";
                    return nome1.compareTo(nome2);
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

    @Transactional(readOnly = true)
    public List<Long> getSquadreIds(Long torneoId) {
        Torneo torneo = torneoRepository.findById(torneoId).orElse(null);
        if (torneo == null || torneo.getSquadre() == null) return new ArrayList<>();
        return torneo.getSquadre().stream().map(Squadra::getId).collect(Collectors.toList());
    }

    @Transactional
    public void deleteTorneo(long id) {
        torneoRepository.deleteById(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional(readOnly = true)
    public void runBenchmark() {
        benchmarkFetchStrategies();
    }

    public void benchmarkFetchStrategies() {
        // Strategia 1: LAZY (N+1)
        long start1 = System.nanoTime();
        List<Torneo> torneiLazy = torneoRepository.findAll();
        for (Torneo t : torneiLazy) {
            if (t.getSquadre() != null) t.getSquadre().size();
        }
        long ms1 = (System.nanoTime() - start1) / 1_000_000;

        // Strategia 2: JOIN FETCH
        long start2 = System.nanoTime();
        List<Torneo> torneiJoin = torneoRepository.findAllWithSquadre();
        for (Torneo t : torneiJoin) {
            if (t.getSquadre() != null) t.getSquadre().size();
        }
        long ms2 = (System.nanoTime() - start2) / 1_000_000;

        // Strategia 3: EntityGraph
        long start3 = System.nanoTime();
        List<Torneo> torneiGraph = torneoRepository.findAllWithEntityGraph();
        for (Torneo t : torneiGraph) {
            if (t.getSquadre() != null) t.getSquadre().size();
        }
        long ms3 = (System.nanoTime() - start3) / 1_000_000;

        log.info("=== BENCHMARK FETCH STRATEGIES ===");
        log.info("LAZY (N+1 queries): {} ms — {} tornei", ms1, torneiLazy.size());
        log.info("JOIN FETCH (1 query): {} ms — {} tornei", ms2, torneiJoin.size());
        log.info("ENTITY GRAPH (1 query): {} ms — {} tornei", ms3, torneiGraph.size());
        log.info("==================================");
    }
}