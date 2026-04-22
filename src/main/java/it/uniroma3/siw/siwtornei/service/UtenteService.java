package it.uniroma3.siw.siwtornei.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.repository.UtenteRepository;
import it.uniroma3.siw.siwtornei.model.Utente;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UtenteService {
    
    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public Utente saveUtente(Utente utente) {
        return utenteRepository.save(utente);
    }

    @Transactional(readOnly = true)
    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Utente findById(Long id) {
        return utenteRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username).orElse(null);
    }

}
