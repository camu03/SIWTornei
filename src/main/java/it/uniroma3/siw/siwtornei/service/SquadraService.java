package it.uniroma3.siw.siwtornei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.siwtornei.repository.SquadraRepository;
import it.uniroma3.siw.siwtornei.model.Squadra;
import java.util.List;
import it.uniroma3.siw.siwtornei.model.Torneo;

@Service // Fondamentale! Dice a Spring che questo è il "cervello"
public class SquadraService {

    // "Inietto" il magazziniere per poterlo usare
    @Autowired
    private SquadraRepository squadraRepository;

    // Operazione di INSERIMENTO / AGGIORNAMENTO (Transazione standard)
    @Transactional
    public Squadra saveSquadra(Squadra squadra) {
        // Qui potremmo aggiungere logica, es: controllare che il nome non sia vuoto
        return squadraRepository.save(squadra);
    }

    // Operazione di SOLA LETTURA (Transazione ottimizzata)
    @Transactional(readOnly = true)
    public List<Squadra> findAll() {
        return squadraRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Squadra findById(Long id) {
        return squadraRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Squadra> findByNome(String nome) {
        return squadraRepository.findByNome(nome);
    }

    @Transactional(readOnly = true)
    public List<Squadra> findByCitta(String citta) {
        return squadraRepository.findByCitta(citta);
    }

    @Transactional(readOnly = true)
    public List<Squadra> findByAnnoFondazione(Integer annoFondazione) {
        return squadraRepository.findByAnnoFondazione(annoFondazione);
    }

    @Transactional(readOnly = true)
    public List<Squadra> findByTorneo(Torneo torneo) {  
        return squadraRepository.findByTorneiContains(torneo);
    }

    //elimina
    @Transactional
    public void deleteSquadra(long id) {
        squadraRepository.deleteById(id);
    }
}