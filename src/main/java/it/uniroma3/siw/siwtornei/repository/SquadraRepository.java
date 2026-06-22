package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import it.uniroma3.siw.siwtornei.model.Squadra;
import it.uniroma3.siw.siwtornei.model.Torneo;

import java.util.List;

public interface SquadraRepository extends JpaRepository<Squadra, Long> {

    List<Squadra> findByNome(String nome);
    List<Squadra> findByCitta(String citta);
    List<Squadra> findByAnnoFondazione(Integer annoFondazione);
    List<Squadra> findByTorneiContains(Torneo torneo);

    @Query("SELECT s FROM Squadra s LEFT JOIN FETCH s.giocatori WHERE s.id = :id")
    Squadra findByIdWithGiocatori(@Param("id") Long id);
}