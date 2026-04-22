package it.uniroma3.siw.siwtornei.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Arbitro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(unique = true, nullable = false)
    private String codiceArbitrale;

    @OneToMany(mappedBy = "arbitro")
    private List<Partita> partite;

    public Arbitro() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public String getCodiceArbitrale() { return codiceArbitrale; }
    public void setCodiceArbitrale(String codiceArbitrale) { this.codiceArbitrale = codiceArbitrale; }
    public List<Partita> getPartite() { return partite; }
    public void setPartite(List<Partita> partite) { this.partite = partite; }
    
}
