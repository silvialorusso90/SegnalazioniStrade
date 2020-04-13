package com.example.segnalazionistrade.segnalazioni;

public class LocationHSos extends LocationH{
    private String tipoSos;

    public String getTipoSos() {
        return tipoSos;
    }

    public void setTipoSos(String tipoSos) {
        this.tipoSos = tipoSos;
    }

    public LocationHSos() {
    }

    public LocationHSos(String tipoSos) {
        this.tipoSos = tipoSos;
    }

    public LocationHSos(float longitude, float latitude, String tipoSos) {
        super(longitude, latitude);
        this.tipoSos = tipoSos;
    }

    public LocationHSos(int id, float longitude, float latitude, String idUser, String tipo, String indirizzo, String tipoSos) {
        super(id, longitude, latitude, idUser, tipo, indirizzo);
        this.tipoSos = tipoSos;
    }

    public LocationHSos(int id, float longitude, float latitude, String tipoSos) {
        super(id, longitude, latitude);
        this.tipoSos = tipoSos;
    }

    public LocationHSos(int id, float longitude, float latitude, String idUser, String tipo, String tipoSos) {
        super(id, longitude, latitude, idUser, tipo);
        this.tipoSos = tipoSos;
    }
}
