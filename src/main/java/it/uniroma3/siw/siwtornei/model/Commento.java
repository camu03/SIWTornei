package it.uniroma3.siw.siwtornei.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Commento {
    
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String testo;
    @Column(nullable = false, length = 1000)
    private LocalDateTime dataOra;

    @ManyToOne
    private Partita partita;
    @ManyToOne
    private Utente utente;

    public Commento() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }
    public LocalDateTime getDataOra() { return dataOra; }
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra; }
    public Partita getPartita() { return partita; }
    public void setPartita(Partita partita) { this.partita = partita; }
    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) { this.utente = utente; }
}
