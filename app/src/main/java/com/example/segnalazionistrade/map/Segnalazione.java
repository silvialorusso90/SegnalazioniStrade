package com.example.segnalazionistrade.map;

public class Segnalazione {

    private String indirizzo;
    private String tipoSegnalazione;
    private String gravita;

    public Segnalazione(String indirizzo, String tipoSegnalazione, String gravita) {
        this.indirizzo = indirizzo;
        this.tipoSegnalazione = tipoSegnalazione;
        this.gravita = gravita;
    }

    public Segnalazione() {
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTipoSegnalazione() {
        return tipoSegnalazione;
    }

    public void setTipoSegnalazione(String tipoSegnalazione) {
        this.tipoSegnalazione = tipoSegnalazione;
    }

    public String getGravita() {
        return gravita;
    }

    public void setGravita(String gravita) {
        this.gravita = gravita;
    }
}
