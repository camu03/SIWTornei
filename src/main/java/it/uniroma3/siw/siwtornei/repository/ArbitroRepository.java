package it.uniroma3.siw.siwtornei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import it.uniroma3.siw.siwtornei.model.Arbitro;

public interface ArbitroRepository extends JpaRepository<Arbitro, Long> {
    Optional<Arbitro> findByCodiceArbitrale(String codiceArbitrale);
}
