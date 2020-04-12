package com.example.segnalazionistrade.chat;

public class Message {

    private String message;
    private String author;
    private String date;
    private String hour;

    public Message(String message, String author, String date, String hour) {
        this.message = message;
        this.author = author;
        this.date = date;
        this.hour = hour;
    }

    public Message() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
