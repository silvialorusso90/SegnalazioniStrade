package com.example.segnalazionistrade;

public class LocationHelper {
    private int id;
    private float Longitude;
    private float Latitude;
    private String gravita;
    String indirizzo;

    public LocationHelper(int id, float longitude, float latitude, String gravita, String indirizzo) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.gravita = gravita;
        this.indirizzo = indirizzo;
    }

    public LocationHelper(int id, float latitude, float longitude, String indirizzo) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.indirizzo = indirizzo;
    }

    public LocationHelper(float latitude, float longitude) {
        Longitude = longitude;
        Latitude = latitude;
    }

    public LocationHelper(float longitude, float latitude, String indirizzo) {
        Longitude = longitude;
        Latitude = latitude;
        this.indirizzo = indirizzo;
    }

    public LocationHelper() {
    }

    public LocationHelper(int id, float latitude, float longitude) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
    }

    public LocationHelper(int idTimeMillis, float longitude, float latitude, String idUser, String gravita, String tipoSegnalazione) {
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

