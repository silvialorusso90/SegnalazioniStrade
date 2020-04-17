package com.example.segnalazionistrade.segnalazioni;

public class LocationHIncidente extends LocationH{
    private String gravita;

    public LocationHIncidente() {
    }

    public LocationHIncidente(String gravita) {
        this.gravita = gravita;
    }

    public LocationHIncidente(float longitude, float latitude, String gravita) {
        super(longitude, latitude);
        this.gravita = gravita;
    }

    public LocationHIncidente(int id, float longitude, float latitude, String idUser, String tipo,
                              String indirizzo, String gravita) {
        super(id, longitude, latitude, idUser, tipo, indirizzo);
        this.gravita = gravita;
    }

    public LocationHIncidente(int id, float longitude, float latitude, String gravita) {
        super(id, longitude, latitude);
        this.gravita = gravita;
    }

    public LocationHIncidente(int id, float longitude, float latitude, String idUser, String tipo,
                              String gravita) {
        super(id, longitude, latitude, idUser, tipo);
        this.gravita = gravita;
    }

    public LocationHIncidente(int id, float longitude, float latitude, String idUser, String tipo,
                              String indirizzo, String data, String ora, String gravita) {
        super(id, longitude, latitude, idUser, tipo, indirizzo, data, ora);
        this.gravita = gravita;
    }

    public String getGravita() {
        return gravita;
    }

    public void setGravita(String gravita) {
        this.gravita = gravita;
    }
}