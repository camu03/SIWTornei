package it.uniroma3.siw.siwtornei.authentication;

import it.uniroma3.siw.siwtornei.model.Utente;
import it.uniroma3.siw.siwtornei.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // FONDAMENTALE! Dice a Spring di usare questo file per i login
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        // 1. Cerca l'utente nel nostro database
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        // 2. Se lo trova, "traduce" il nostro Utente nel formato che piace a Spring Security
        return User.builder()
                .username(utente.getUsername())
                .password(utente.getPassword()) // La password criptata dal DB
                .authorities(utente.getRuolo()) // Il ruolo (es. ADMIN o USER)
                .build();
    }
}