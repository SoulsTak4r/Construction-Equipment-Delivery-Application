package com.example.constructionequipmentapp;

public class Orders {
    String name;
    String date;
    String location;
    String size;

    public Orders(String name, String date, String location, String size) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.size = size;
    }

    public Orders() {
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getSize() {
        return size;
    }
}
