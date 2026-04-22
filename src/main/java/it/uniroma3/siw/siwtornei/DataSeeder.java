package it.uniroma3.siw.siwtornei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.siwtornei.model.*;
import it.uniroma3.siw.siwtornei.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private TorneoService torneoService;
    @Autowired
    private SquadraService squadraService;
    @Autowired
    private GiocatoreService giocatoreService;
    @Autowired
    private ArbitroService arbitroService;
    @Autowired
    private PartitaService partitaService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        // Controlliamo se il DB è vuoto prima di inserire i dati
        if (torneoService.findAll().isEmpty()) {
            
            System.out.println("--- POPOLAMENTO DATABASE IN CORSO ---");

            // 1. Creazione Torneo (nome, anno, descrizione)
            Torneo torneo = new Torneo();
            torneo.setNome("Coppa di Lega Amatoriale");
            torneo.setAnno(2026);
            torneo.setDescrizione("Torneo estivo per squadre locali");
            torneoService.saveTorneo(torneo);

            // 2. Creazione Squadre (nome, anno di fondazione, città)
            Squadra sq1 = new Squadra();
            sq1.setNome("Lupi F.C.");
            sq1.setAnnoFondazione(2010);
            sq1.setCitta("Roma");
            
            // Gestione della ManyToMany (Squadra è la proprietaria)
            List<Torneo> torneiSq1 = new ArrayList<>();
            torneiSq1.add(torneo);
            sq1.setTornei(torneiSq1);
            squadraService.saveSquadra(sq1);

            Squadra sq2 = new Squadra();
            sq2.setNome("Aquile United");
            sq2.setAnnoFondazione(2015);
            sq2.setCitta("Roma");
            
            List<Torneo> torneiSq2 = new ArrayList<>();
            torneiSq2.add(torneo);
            sq2.setTornei(torneiSq2);
            squadraService.saveSquadra(sq2);

            // 3. Creazione Giocatori (nome, cognome, data di nascita, ruolo, altezza)
            Giocatore g1 = new Giocatore();
            g1.setNome("Mario");
            g1.setCognome("Rossi");
            g1.setDataDiNascita(LocalDate.of(1995, 5, 20));
            g1.setRuolo("Attaccante");
            g1.setAltezza(180);
            g1.setSquadra(sq1);
            giocatoreService.saveGiocatore(g1);

            Giocatore g2 = new Giocatore();
            g2.setNome("Luca");
            g2.setCognome("Bianchi");
            g2.setDataDiNascita(LocalDate.of(1998, 11, 10));
            g2.setRuolo("Portiere");
            g2.setAltezza(190);
            g2.setSquadra(sq2);
            giocatoreService.saveGiocatore(g2);

            // 4. Creazione Arbitro (nome, cognome, codice arbitrale)
            Arbitro arbitro = new Arbitro();
            arbitro.setNome("Giovanni");
            arbitro.setCognome("Fischietti");
            arbitro.setCodiceArbitrale("ARB-001");
            arbitroService.saveArbitro(arbitro);

            // 5. Creazione Partita (data e ora, luogo, goalsHome, goalsAway, stato)
            Partita partita = new Partita();
            partita.setTorneo(torneo);
            partita.setSquadraHome(sq1);
            partita.setSquadraAway(sq2);
            partita.setArbitro(arbitro);
            partita.setDataOra(LocalDateTime.now().minusDays(1)); 
            partita.setLuogo("Stadio Comunale");
            partita.setStato(StatoPartita.PLAYED); // Assumendo che tu abbia l'enum StatoPartita
            partita.setGolHome(2);
            partita.setGolAway(1);
            partitaService.savePartita(partita);

            System.out.println("--- DATI INSERITI CON SUCCESSO! ---");
        }
    }
}