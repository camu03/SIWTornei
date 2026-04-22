package it.uniroma3.siw.siwtornei.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.repository.GiocatoreRepository;
import it.uniroma3.siw.siwtornei.model.Giocatore;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class GiocatoreService {
    
    @Autowired
    private GiocatoreRepository giocatoreRepository;

    @Transactional
    public Giocatore saveGiocatore(Giocatore giocatore) {
        return giocatoreRepository.save(giocatore);
    }
    @Transactional(readOnly = true)
    public List<Giocatore> findAll() {
        return giocatoreRepository.findAll();
    }   

    @Transactional(readOnly = true)
    public Giocatore findById(Long id) {
        return giocatoreRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Giocatore> findByNome(String nome) {
        return giocatoreRepository.findByNome(nome);
    }

    @Transactional(readOnly = true)
    public List<Giocatore> findByCognome(String cognome) {
        return giocatoreRepository.findByCognome(cognome);
    }

    @Transactional(readOnly = true)
    public List<Giocatore> findByAltezza(Float altezza) {
        return giocatoreRepository.findByAltezza(altezza);
    }

    @Transactional(readOnly = true)
    public List<Giocatore> findByRuolo(String ruolo) {
        return giocatoreRepository.findByRuolo(ruolo);
    }

}
