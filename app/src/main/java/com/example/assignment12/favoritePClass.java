package com.example.assignment12;

public class favoritePClass {

    int id;
    String address;
    Double favLatitude,favLongitude;
    String date;

    public favoritePClass(int id, String address, Double favLatitude, Double favLongitude, String date) {
        this.id = id;
        this.address = address;
        this.favLatitude = favLatitude;
        this.favLongitude = favLongitude;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getFavLatitude() {
        return String.valueOf(favLatitude);
    }

    public String getFavLongitude() {
        return String.valueOf(favLongitude);
    }

    public String getDate() {
        return date;
    }

}
