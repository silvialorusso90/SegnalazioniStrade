package com.example.segnalazionistrade.segnalazioni;
public class LocationHelper {
    private int id;
    private float Longitude;
    private float Latitude;
    private String idUser, gravita, tipoSegnalazione;
    String indirizzo;

    public LocationHelper(int id, float longitude, float latitude, String idUser, String gravita,
                          String tipoSegnalazione, String indirizzo) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.idUser = idUser;
        this.gravita = gravita;
        this.tipoSegnalazione = tipoSegnalazione;
        this.indirizzo = indirizzo;
    }

    public LocationHelper() {
    }

    public LocationHelper(int id, float longitude, float latitude, String idUser, String gravita, String tipoSegnalazione) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.idUser = idUser;
        this.gravita = gravita;
        this.tipoSegnalazione = tipoSegnalazione;
    }

    public LocationHelper(float longitude, float latitude) {
        Longitude = longitude;
        Latitude = latitude;
    }

    public String getTipoSegnalazione() {
        return tipoSegnalazione;
    }

    public void setTipoSegnalazione(String tipoSegnalazione) {
        this.tipoSegnalazione = tipoSegnalazione;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public String getGravita() {
        return gravita;
    }

    public void setGravita(String gravita) {
        this.gravita = gravita;
    }
}
