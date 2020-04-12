package com.example.segnalazionistrade.segnalazioni;

public class LocationH {
    private int id;
    private float longitude, latitude;
    private String idUser, tipo, indirizzo;

    public LocationH() {
    }

    public LocationH(int id, float longitude, float latitude, String idUser, String tipo, String indirizzo) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idUser = idUser;
        this.tipo = tipo;
        this.indirizzo = indirizzo;
    }

    public LocationH(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationH(int idTimeMillis, float longitude, float latitude, String idUser) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
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
