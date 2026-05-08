function TorneiRicerca() {
    const [tornei, setTornei] = React.useState([]);
    const [ricerca, setRicerca] = React.useState('');

    React.useEffect(() => {
        fetch('/api/tornei')
            .then(response => response.json())
            .then(data => setTornei(data))
            .catch(error => console.error("Errore nel caricamento:", error));
    }, []);

    const torneiFiltrati = tornei.filter(torneo => 
        torneo.nome.toLowerCase().includes(ricerca.toLowerCase()) || 
        (torneo.descrizione && torneo.descrizione.toLowerCase().includes(ricerca.toLowerCase()))
    );

    return (
        <div>
            <input 
                type="text" 
                placeholder="Cerca un torneo per nome o descrizione..." 
                value={ricerca}
                onChange={(e) => setRicerca(e.target.value)}
                style={{ width: '80%', padding: '15px', marginBottom: '30px', borderRadius: '8px', border: '1px solid #ccc', fontSize: '16px', background: '#ffffff', color: '#222222' }}
            />

            {torneiFiltrati.length === 0 ? (
                <p className="empty-state">Nessun torneo trovato con questa ricerca.</p>
            ) : (
                <div className="grid tournaments-grid" style={{ marginTop: '0' }}>
                    {torneiFiltrati.map(torneo => (
                        <div key={torneo.id} className="card torneo-card">
                            <a href={`/torneo/${torneo.id}`} className="titolo-torneo">{torneo.nome}</a>
                            <p><strong>Edizione:</strong> {torneo.anno}</p>
                            <p><em>{torneo.descrizione}</em></p>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
}

const root = ReactDOM.createRoot(document.getElementById('react-app-root'));
root.render(<TorneiRicerca />);