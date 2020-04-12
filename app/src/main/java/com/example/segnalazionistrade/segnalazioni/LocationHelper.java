package com.example.segnalazionistrade.segnalazioni;
public class LocationHelper {

    private int id;
    private float longitude, latitude;
    private String idUser, gravita, tipo, indirizzo;

    LocationHelper(int id, float longitude, float latitude, String idUser, String gravita,
                          String tipo, String indirizzo) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idUser = idUser;
        this.gravita = gravita;
        this.tipo = tipo;
        this.indirizzo = indirizzo;
    }

    LocationHelper() {
    }


    public LocationHelper(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public LocationHelper(int idTimeMillis, float longitude, float latitude, String idUser) {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getGravita() {
        return gravita;
    }

    public void setGravita(String gravita) {
        this.gravita = gravita;
    }
}