package it.uniroma3.siw.siwtornei.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Partita {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataOra;
    @Column(nullable = false)
    private String luogo;
    @Column(nullable = false)
    private Integer golHome;
    @Column(nullable = false)
    private Integer golAway;
    @Enumerated(EnumType.STRING)
    private StatoPartita stato;

    @ManyToOne
    private Torneo torneo;
    @ManyToOne
    private Squadra squadraHome;
    @ManyToOne
    private Squadra squadraAway;
    @ManyToOne
    private Arbitro arbitro;

    public Partita() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataOra() { return dataOra; }
    public void setDataOra(LocalDateTime dataOra) { this.dataOra = dataOra; }
    public String getLuogo() { return luogo; }
    public void setLuogo(String luogo) { this.luogo = luogo; }
    public Integer getGolHome() { return golHome; }
    public void setGolHome(Integer golHome) { this.golHome = golHome; }
    public Integer getGolAway() { return golAway; }
    public void setGolAway(Integer golAway) { this.golAway = golAway; }
    public StatoPartita getStato() { return stato; }
    public void setStato(StatoPartita stato) { this.stato = stato; }
    public Torneo getTorneo() { return torneo; }
    public void setTorneo(Torneo torneo) { this.torneo = torneo; }
    public Squadra getSquadraHome() { return squadraHome; }
    public void setSquadraHome(Squadra squadraHome) { this.squadraHome = squadraHome; }
    public Squadra getSquadraAway() { return squadraAway; }
    public void setSquadraAway(Squadra squadraAway) { this.squadraAway = squadraAway; }
    public Arbitro getArbitro() { return arbitro; }
    public void setArbitro(Arbitro arbitro) { this.arbitro = arbitro; }
    
}
