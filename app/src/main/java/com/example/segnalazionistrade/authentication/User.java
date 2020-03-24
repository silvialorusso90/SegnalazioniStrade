package com.example.segnalazionistrade.authentication;

public class User {
    private String nome;
    private String cognome;
    private String email;

    public User() {
    }

    public User(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String noome) {
        this.nome = noome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}