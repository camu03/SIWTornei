package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.siwtornei.model.Torneo;
//import java.util.List;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    Torneo findByNome(String nome);
}
