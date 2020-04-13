package com.example.segnalazionistrade.segnalazioni;

public class LocationHIncidente extends LocationH{
    private String gravita;

    public LocationHIncidente() {
    }

    public LocationHIncidente(String gravita) {
        this.gravita = gravita;
    }

    public LocationHIncidente(int id, float longitude, float latitude, String idUser, String tipoSegnalazione, String indirizzo, String gravita) {
        super(id, longitude, latitude, idUser, tipoSegnalazione, indirizzo);
        this.gravita = gravita;
    }

    public LocationHIncidente(int id, float longitude, float latitude, String gravita) {
        super(id, longitude, latitude);
        this.gravita = gravita;
    }

    public LocationHIncidente(int id, float longitude, float latitude, String idUser, String tipoSegnalazione, String gravita) {
        super(id, longitude, latitude, idUser, tipoSegnalazione);
        this.gravita = gravita;
    }

    public String getGravita() {
        return gravita;
    }

    public void setGravita(String gravita) {
        this.gravita = gravita;
    }
}