package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import it.uniroma3.siw.siwtornei.model.Torneo;
import java.util.List;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    Torneo findByNome(String nome);

    @Query("SELECT DISTINCT t FROM Torneo t LEFT JOIN FETCH t.squadre")
    List<Torneo> findAllWithSquadre();

    @EntityGraph(value = "Torneo.squadre", type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT DISTINCT t FROM Torneo t")
    List<Torneo> findAllWithEntityGraph();
}
