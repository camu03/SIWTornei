package it.uniroma3.siw.siwtornei.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity 
public class Giocatore {

    @Id // Indica che questo campo è la Chiave Primaria
    @GeneratedValue(strategy = GenerationType.AUTO) // Fa generare l'ID in automatico al database
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false)
    private LocalDate dataDiNascita;
    @Column(nullable = false)
    private String ruolo;
    @Column(nullable = false)
    private Integer altezza;

    @ManyToOne
    private Squadra squadra;

    // Costruttore vuoto obbligatorio per JPA
    public Giocatore() {
    }

    // Qui andranno inseriti tutti i metodi Getter e Setter per ogni attributo...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }  
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public LocalDate getDataDiNascita() { return dataDiNascita; }
    public void setDataDiNascita(LocalDate dataDiNascita) { this.dataDiNascita = dataDiNascita; }
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    public Integer getAltezza() { return altezza; }
    public void setAltezza(Integer altezza) { this.altezza = altezza; }
    public Squadra getSquadra() { return squadra; }
    public void setSquadra(Squadra squadra) { this.squadra = squadra; }
}