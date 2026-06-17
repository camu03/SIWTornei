package it.uniroma3.siw.siwtornei.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.repository.UtenteRepository;
import it.uniroma3.siw.siwtornei.model.Utente;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Utente registra(Utente utente) {
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
    }

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
