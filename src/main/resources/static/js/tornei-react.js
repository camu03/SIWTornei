(function () {
    var e = React.createElement;

    function TorneiRicerca() {
        var state = React.useState([]);
        var tornei = state[0]; var setTornei = state[1];

        var searchState = React.useState('');
        var ricerca = searchState[0]; var setRicerca = searchState[1];

        React.useEffect(function () {
            fetch('/api/tornei')
                .then(function (r) { return r.json(); })
                .then(function (data) { setTornei(data); })
                .catch(function (err) { console.error("Errore nel caricamento:", err); });
        }, []);

        var torneiFiltrati = tornei.filter(function (t) {
            return t.nome.toLowerCase().startsWith(ricerca.toLowerCase());
        });

        return e('div', null,
            e('input', {
                type: 'text',
                placeholder: 'Cerca un torneo per nome o descrizione...',
                value: ricerca,
                onChange: function (ev) { setRicerca(ev.target.value); },
                style: {
                    width: '80%', padding: '15px', marginBottom: '30px',
                    borderRadius: '8px', border: '1px solid #ccc',
                    fontSize: '16px', background: '#ffffff', color: '#222222'
                }
            }),
            torneiFiltrati.length === 0
                ? e('p', { className: 'empty-state' }, 'Nessun torneo trovato con questa ricerca.')
                : e('div', { className: 'grid tournaments-grid', style: { marginTop: '0' } },
                    torneiFiltrati.map(function (torneo) {
                        return e('div', { key: torneo.id, className: 'card torneo-card' },
                            e('a', { href: '/torneo/' + torneo.id, className: 'titolo-torneo' }, torneo.nome),
                            e('p', null, e('strong', null, 'Edizione: '), torneo.anno),
                            e('p', null, e('em', null, torneo.descrizione))
                        );
                    })
                )
        );
    }

    ReactDOM.createRoot(document.getElementById('react-app-root')).render(e(TorneiRicerca));
})();
