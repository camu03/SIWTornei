package it.uniroma3.siw.siwtornei.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Squadra {
 
    @Id // Indica che questo campo è la Chiave Primaria
    @GeneratedValue(strategy = GenerationType.AUTO) // Fa generare l'ID in automatico al database
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;
    @Column(nullable = false)
    private Integer annoFondazione;
    @Column(nullable = false)
    private String citta;

    @OneToMany(mappedBy = "squadra")
    private List<Giocatore> giocatori;

    @ManyToMany(mappedBy = "squadre")
    private List<Torneo> tornei;
        
    public Squadra(){

    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }  
    public Integer getAnnoFondazione() { return annoFondazione; }
    public void setAnnoFondazione(Integer annoFondazione) { this.annoFondazione = annoFondazione; }
    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }
    public List<Giocatore> getGiocatori() { return giocatori; }
    public void setGiocatori(List<Giocatore> giocatori) { this.giocatori = giocatori; }
    public List<Torneo> getTornei() { return tornei; }
    public void setTornei(List<Torneo> tornei) { this.tornei = tornei; }
}
