package com.example.parkingreservation.models;

public class Reservation {
    int reservation_id;
    String parking;
    String user;
    String date;
    String time;

    public Reservation(int id, String user, String parking, String date, String time) {
        this.reservation_id = id;
        this.user = user;
        this.parking = parking;
        this.date = date;
        this.time = time;
    }

    public String getParking() {
        return parking;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
