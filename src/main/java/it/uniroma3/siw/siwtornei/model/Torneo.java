package it.uniroma3.siw.siwtornei.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Torneo {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
    @Column(nullable = false)
    private Integer anno;
    @Column(nullable = false)
    private String descrizione;

    @ManyToMany(mappedBy = "tornei")
    private List<Squadra> squadre;
    @OneToMany(mappedBy = "torneo")
    private List<Partita> partite;

    public Torneo() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getAnno() { return anno; }
    public void setAnno(Integer anno) { this.anno = anno; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public List<Squadra> getSquadre() { return squadre; }
    public void setSquadre(List<Squadra> squadre) { this.squadre = squadre; }
    public List<Partita> getPartite() { return partite; }
    public void setPartite(List<Partita> partite) { this.partite = partite; }

}
