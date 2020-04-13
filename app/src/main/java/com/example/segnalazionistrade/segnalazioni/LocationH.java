package com.example.segnalazionistrade.segnalazioni;

public class LocationH {
    private int id;
    private float Longitude;
    private float Latitude;
    private String idUser, tipo, indirizzo;

    LocationH() {
    }

    public LocationH(float longitude, float latitude) {
        Longitude = longitude;
        Latitude = latitude;
    }

    LocationH(int id, float longitude, float latitude, String idUser, String tipo, String indirizzo) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.idUser = idUser;
        this.tipo = tipo;
        this.indirizzo = indirizzo;
    }

    LocationH(int id, float longitude, float latitude) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
    }

    LocationH(int id, float longitude, float latitude, String idUser, String tipo) {
        this.id = id;
        Longitude = longitude;
        Latitude = latitude;
        this.idUser = idUser;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
