package com.example.segnalazionistrade.segnalazioni;

public class LocationH {
    private int id;
    private float Longitude;
    private float Latitude;
    private String idUser, tipoSegnalazione, indirizzo;

    public LocationH() {
    }

    public LocationH(float longitude, float latitude) {
        Longitude = longitude;
        Latitude = latitude;
    }

    public LocationH(int id, float longitude, float latitude, String idUser, String tipoSegnalazione, String indirizzo) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.idUser = idUser;
        this.tipoSegnalazione = tipoSegnalazione;
        this.indirizzo = indirizzo;
    }

    public LocationH(int id, float longitude, float latitude) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
    }

    public LocationH(int id, float longitude, float latitude, String idUser, String tipoSegnalazione) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.idUser = idUser;
        this.tipoSegnalazione = tipoSegnalazione;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTipoSegnalazione() {
        return tipoSegnalazione;
    }

    public void setTipoSegnalazione(String tipoSegnalazione) {
        this.tipoSegnalazione = tipoSegnalazione;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
