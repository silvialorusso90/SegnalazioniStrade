package com.example.segnalazionistrade.segnalazioni;

public class LocationHTraffico extends LocationH {
    private String intensita;

    public LocationHTraffico() {
    }

    public LocationHTraffico(String intensita) {
        this.intensita = intensita;
    }

    public LocationHTraffico(float longitude, float latitude, String intensita) {
        super(longitude, latitude);
        this.intensita = intensita;
    }

    public LocationHTraffico(int id, float longitude, float latitude, String idUser, String tipo, String indirizzo, String intensita) {
        super(id, longitude, latitude, idUser, tipo, indirizzo);
        this.intensita = intensita;
    }

    public LocationHTraffico(int id, float longitude, float latitude, String intensita) {
        super(id, longitude, latitude);
        this.intensita = intensita;
    }

    public LocationHTraffico(int id, float longitude, float latitude, String idUser, String tipo, String intensita) {
        super(id, longitude, latitude, idUser, tipo);
        this.intensita = intensita;
    }

    public String getIntensita() {
        return intensita;
    }

    public void setIntensita(String intensita) {
        this.intensita = intensita;
    }
}
