package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.Torneo;

import java.util.List;

public interface SquadraRepository extends JpaRepository<Squadra, Long> {
    
    List<Squadra> findByNome(String nome);
    List<Squadra> findByCitta(String citta);
    List<Squadra> findByAnnoFondazione(Integer annoFondazione);
    List<Squadra> findByTorneiContains(Torneo torneo);
}