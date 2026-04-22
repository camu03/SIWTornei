package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import it.uniroma3.siw.siwtornei.model.Partita;
import it.uniroma3.siw.siwtornei.model.StatoPartita;
import it.uniroma3.siw.siwtornei.model.Torneo;

public interface PartitaRepository extends JpaRepository<Partita, Long> {
    // Trova tutte le partite di un torneo (per il calendario)
List<Partita> findByTorneo(Torneo torneo);

// Trova le partite di un torneo filtrate per stato (utile per la classifica)
List<Partita> findByTorneoAndStato(Torneo torneo, StatoPartita stato);
List<Partita> findByTorneoOrderByDataOraAsc(Torneo torneo);
}
