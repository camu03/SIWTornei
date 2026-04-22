package it.uniroma3.siw.siwtornei.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.repository.ArbitroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.siwtornei.model.Arbitro; 
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ArbitroService {

    @Autowired
    private ArbitroRepository arbitroRepository;

    @Transactional
    public Arbitro saveArbitro(Arbitro arbitro) {
        return arbitroRepository.save(arbitro);
    }

    @Transactional(readOnly = true)
    public List<Arbitro> findAll() {
        return arbitroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Arbitro findById(Long id) {
        return arbitroRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Arbitro findByCodiceArbitrale(String codiceArbitro) {
        return arbitroRepository.findByCodiceArbitrale(codiceArbitro).orElse(null);
    }
}