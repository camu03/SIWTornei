package it.uniroma3.siw.siwtornei.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Utente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String ruolo;

    @OneToMany(mappedBy = "utente")
    private List<Commento> commenti;

    public Utente() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }
    public List<Commento> getCommenti() { return commenti; }
    public void setCommenti(List<Commento> commenti) { this.commenti = commenti; }
    
}
