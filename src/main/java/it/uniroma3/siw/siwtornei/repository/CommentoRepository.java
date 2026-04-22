package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import it.uniroma3.siw.siwtornei.model.Commento;
import it.uniroma3.siw.siwtornei.model.Partita;

public interface CommentoRepository extends JpaRepository<Commento, Long> {
    List<Commento> findByPartita(Partita partita);
}
