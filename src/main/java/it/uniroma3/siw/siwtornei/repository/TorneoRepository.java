package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.siwtornei.model.Torneo;

public interface TorneoRepository extends JpaRepository<Torneo, Long> {
    Torneo findByNome(String nome);
    
}
