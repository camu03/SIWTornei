package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.siwtornei.model.Giocatore;
import java.util.List;


public interface GiocatoreRepository extends JpaRepository<Giocatore, Long> {
    List<Giocatore> findByNome(String nome);
    List<Giocatore> findByCognome(String cognome);
    List<Giocatore> findByAltezza(Float altezza);
    List<Giocatore> findByRuolo(String ruolo);
}
