package it.uniroma3.siw.siwtornei.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.siwtornei.model.Commento;
import it.uniroma3.siw.siwtornei.repository.CommentoRepository;
import java.util.List;
import it.uniroma3.siw.siwtornei.model.Partita;

@Service
public class CommentoService {
    
    private final CommentoRepository commentoRepository;

    public CommentoService(CommentoRepository commentoRepository) {
        this.commentoRepository = commentoRepository;
    }

    @Transactional
    public void saveCommento(Commento commento) {
        commentoRepository.save(commento);
    }

    @Transactional(readOnly = true)
    public List<Commento> findAll() {
        return commentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Commento findById(Long id) {
        return commentoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Commento> findByPartita(Partita partita) {
        return commentoRepository.findByPartita(partita);
    }

    @Transactional
    public void deleteCommento(Commento commento) {
        commentoRepository.delete(commento);
    }
}
