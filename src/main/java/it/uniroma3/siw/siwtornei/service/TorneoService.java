package it.uniroma3.siw.siwtornei.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.repository.TorneoRepository;
import org.springframework.transaction.annotation.Transactional;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TorneoService {

    private static final Logger log = LoggerFactory.getLogger(TorneoService.class);

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

    /**
     * ANALISI SPERIMENTALE FETCH STRATEGIES
     *
     * Confronta tre strategie di accesso ai dati per il caricamento
     * dei tornei con le loro squadre partecipanti.
     *
     * Strategia 1 - LAZY (default JPA):
     *   findAll() carica solo i tornei. Le squadre vengono caricate
     *   separatamente per ogni torneo quando si accede a getSquadre().
     *   Genera il problema N+1: 1 query per i tornei + N query (una per torneo).
     *
     * Strategia 2 - JOIN FETCH (query esplicita):
     *   Una singola query SQL con LEFT JOIN carica tornei e squadre insieme.
     *   Elimina N+1 al costo di un JOIN più pesante, ma molto più efficiente
     *   quando si devono visitare le squadre di tutti i tornei.
     *
     * Risultati attesi:
     *   Con pochi dati le differenze sono minime; con molti tornei/squadre
     *   LAZY degrada linearmente (N+1) mentre JOIN FETCH rimane costante.
     */
    @Transactional(readOnly = true)
    public Map<String, String> benchmarkFetchStrategies() {
        Map<String, String> risultati = new java.util.LinkedHashMap<>();

        // --- STRATEGIA 1: LAZY ---
        long start1 = System.nanoTime();
        List<Torneo> torneiLazy = torneoRepository.findAll();
        for (Torneo t : torneiLazy) {
            int size = (t.getSquadre() != null) ? t.getSquadre().size() : 0; // trigghera lazy load
        }
        long ms1 = (System.nanoTime() - start1) / 1_000_000;
        log.info("[BENCHMARK] LAZY - {} tornei caricati in {} ms (genera {} query SQL)",
                torneiLazy.size(), ms1, 1 + torneiLazy.size());
        risultati.put("LAZY",
                ms1 + " ms — " + (1 + torneiLazy.size()) + " query SQL (1 per i tornei + 1 per ogni torneo → N+1 problem)");

        // --- STRATEGIA 2: JOIN FETCH ---
        long start2 = System.nanoTime();
        List<Torneo> torneiJoin = torneoRepository.findAllWithSquadre();
        for (Torneo t : torneiJoin) {
            int size = (t.getSquadre() != null) ? t.getSquadre().size() : 0; // già in memoria, nessuna query
        }
        long ms2 = (System.nanoTime() - start2) / 1_000_000;
        log.info("[BENCHMARK] JOIN FETCH - {} tornei caricati in {} ms (1 sola query SQL con JOIN)",
                torneiJoin.size(), ms2);
        risultati.put("JOIN FETCH",
                ms2 + " ms — 1 sola query SQL con LEFT JOIN (nessun N+1)");

        // --- DISCUSSIONE ---
        risultati.put("DISCUSSIONE",
                "Con " + torneiLazy.size() + " tornei: LAZY genera " + (1 + torneiLazy.size()) +
                " query, JOIN FETCH ne genera 1. " +
                "All'aumentare dei dati, LAZY degrada linearmente mentre JOIN FETCH rimane costante. " +
                "Nel progetto si usa JOIN FETCH dove servono le squadre (classifica, form modifica), " +
                "e LAZY dove bastano solo i dati base del torneo (elenco tornei).");

        return risultati;
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
}