-- ==========================================
-- POPOLAMENTO DATABASE - SIW TORNEI
-- BASATO ESATTAMENTE SULLE ENTITA' JAVA 
-- ==========================================

-- 1. CREAZIONE TORNEI
INSERT INTO torneo (id, nome, anno, descrizione) VALUES 
(1, 'Champions League SIW', 2026, 'Il torneo più prestigioso a livello europeo per le squadre amatoriali.'),
(2, 'Coppa Italia SIW', 2026, 'La coppa nazionale che mette di fronte le migliori squadre del territorio.');

-- 2. CREAZIONE SQUADRE (Usa anno_fondazione)
INSERT INTO squadra (id, nome, anno_fondazione, citta) VALUES 
(1, 'Lupi F.C.', 1927, 'Roma'),
(2, 'Aquile United', 1900, 'Roma'),
(3, 'Tigri Roma', 2010, 'Roma'),
(4, 'Leoni Milano', 1899, 'Milano'),
(5, 'Delfini Napoli', 1926, 'Napoli'),
(6, 'Falchi Torino', 1897, 'Torino');

-- 3. ABBINAMENTI SQUADRE - TORNEI (Tabella "torneo_squadre", colonna "squadre_id")
INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (1, 1);
INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (1, 2);
INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (1, 3);
INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (1, 4);

INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (2, 1);
INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (2, 2);
INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (2, 5);
INSERT INTO torneo_squadre (torneo_id, squadre_id) VALUES (2, 6);

-- 4. CREAZIONE ARBITRI
INSERT INTO arbitro (id, nome, cognome, codice_arbitrale) VALUES 
(1, 'Pierluigi', 'Collina', 'ARB-001-AIA'),
(2, 'Nicola', 'Rizzoli', 'ARB-002-AIA');

-- 5. CREAZIONE GIOCATORI (Altezza in Integer, es. 180)
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, altezza, squadra_id) VALUES 
(1, 'Francesco', 'Totti', '1976-09-27', 'Attaccante', 180, 1),
(2, 'Daniele', 'De Rossi', '1983-07-24', 'Centrocampista', 184, 1),
(3, 'Alisson', 'Becker', '1992-10-02', 'Portiere', 193, 1),
(4, 'Ciro', 'Immobile', '1990-02-20', 'Attaccante', 185, 2),
(5, 'Sergej', 'Milinkovic', '1995-02-27', 'Centrocampista', 191, 2),
(6, 'Ivan', 'Provedel', '1994-03-17', 'Portiere', 192, 2),
(7, 'Paulo', 'Dybala', '1993-11-15', 'Attaccante', 177, 3),
(8, 'Lorenzo', 'Pellegrini', '1996-06-19', 'Centrocampista', 186, 3),
(9, 'Rafael', 'Leao', '1999-06-10', 'Attaccante', 188, 4),
(10, 'Theo', 'Hernandez', '1997-10-06', 'Difensore', 184, 4),
(11, 'Victor', 'Osimhen', '1998-12-29', 'Attaccante', 185, 5),
(12, 'Khvicha', 'Kvaratskhelia', '2001-02-12', 'Attaccante', 183, 5),
(13, 'Dusan', 'Vlahovic', '2000-01-28', 'Attaccante', 190, 6),
(14, 'Federico', 'Chiesa', '1997-10-25', 'Attaccante', 175, 6);

-- 7. CREAZIONE PARTITE DI ESEMPIO
-- Partita Già Giocata
INSERT INTO partita (id, data_ora, luogo, gol_home, gol_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id) 
VALUES (1, '2026-05-01 20:45:00', 'Stadio Olimpico', 2, 1, 'PLAYED', 1, 1, 2, 1);

-- Partita Programmata (Gol sono null)
INSERT INTO partita (id, data_ora, luogo, gol_home, gol_away, stato, torneo_id, squadra_home_id, squadra_away_id, arbitro_id) 
VALUES (2, '2026-06-15 21:00:00', 'Stadio San Siro', null, null, 'SCHEDULED', 1, 4, 3, 2);


-- ==========================================
-- AGGIORNAMENTO SEQUENZE
-- ==========================================
ALTER SEQUENCE torneo_seq RESTART WITH 10;
ALTER SEQUENCE squadra_seq RESTART WITH 10;
ALTER SEQUENCE arbitro_seq RESTART WITH 10;
ALTER SEQUENCE giocatore_seq RESTART WITH 30;
ALTER SEQUENCE utente_seq RESTART WITH 10;
ALTER SEQUENCE partita_seq RESTART WITH 10;
ALTER SEQUENCE commento_seq RESTART WITH 10;