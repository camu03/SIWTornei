package it.uniroma3.siw.siwtornei.service;

import org.springframework.stereotype.Service;
import it.uniroma3.siw.siwtornei.repository.ArbitroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import it.uniroma3.siw.siwtornei.model.Arbitro;
import it.uniroma3.siw.siwtornei.model.Partita;
import it.uniroma3.siw.siwtornei.repository.PartitaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ArbitroService {

    @Autowired
    private ArbitroRepository arbitroRepository;
    @Autowired
    private PartitaRepository partitaRepository;

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

    @Transactional
    public void deleteArbitro(Long id) {
     
        Arbitro arbitro = arbitroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arbitro non trovato"));

        // 2. Togliamo l'associazione da tutte le partite che dirige
        if (arbitro.getPartite() != null) {
            for (Partita partita : arbitro.getPartite()) {
                partita.setArbitro(null); // La partita ora è "Da assegnare"
                partitaRepository.save(partita);
            }
        }

        arbitroRepository.delete(arbitro);
    }
}
