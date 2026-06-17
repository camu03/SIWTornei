package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import it.uniroma3.siw.siwtornei.model.Torneo;
import java.util.List;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    Torneo findByNome(String nome);

    // JOIN FETCH: carica squadre in una sola query SQL
    @Query("SELECT DISTINCT t FROM Torneo t LEFT JOIN FETCH t.squadre WHERE t.id = :id")
    Torneo findByIdWithSquadre(@Param("id") Long id);

    // JOIN FETCH su tutti i tornei con squadre
    @Query("SELECT DISTINCT t FROM Torneo t LEFT JOIN FETCH t.squadre")
    List<Torneo> findAllWithSquadre();
}
