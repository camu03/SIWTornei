package it.uniroma3.siw.siwtornei.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.model.Partita;
import it.uniroma3.siw.siwtornei.repository.PartitaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import it.uniroma3.siw.siwtornei.model.Torneo;
import it.uniroma3.siw.siwtornei.model.StatoPartita;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Service
public class PartitaService {
    
    private final PartitaRepository partitaRepository;

    public PartitaService(PartitaRepository partitaRepository) {
        this.partitaRepository = partitaRepository;
    }

    @Transactional
    public Partita savePartita(Partita partita) {
        return partitaRepository.save(partita);
    }

    @Transactional(readOnly = true)
    public Partita findById(Long id) {
        return partitaRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Partita> findAll() {
        return partitaRepository.findAll();
    }

    @Transactional(readOnly = true) 
    public List<Partita> findByTorneoAndStato(Torneo torneo, StatoPartita stato) {
        return partitaRepository.findByTorneoAndStato(torneo, stato);
    }

    @Transactional(readOnly = true)
    public List<Partita> findByTorneo(Torneo torneo) {
        return partitaRepository.findByTorneo(torneo);
    }

    @Transactional(readOnly = true)
    public List<Partita> findPartiteDelGiorno() {
        LocalDateTime inizio = LocalDate.now().atStartOfDay();
        LocalDateTime fine = inizio.plusDays(1);
    return partitaRepository.findByDataOraBetween(inizio, fine);
    }

    //elimina
    @Transactional
    public void deletePartita(long id) {
        partitaRepository.deleteById(id);
    }
}    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /* @Transactional(readOnly = true)
    public List<Partita> findByTorneoOrderByDataOraAsc(Torneo torneo) {
        return partitaRepository.findByTorneoOrderByDataOraAsc(torneo);
    }

    @Transactional(readOnly = true)
    public List<Partita> findByTorneoOrderByDataOraDesc(Torneo torneo) {
        return partitaRepository.findByTorneoOrderByDataOraDesc(torneo);
    }*/
