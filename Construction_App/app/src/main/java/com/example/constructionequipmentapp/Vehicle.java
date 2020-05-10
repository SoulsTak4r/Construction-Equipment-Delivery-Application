package com.example.constructionequipmentapp;

public class Vehicle {
    String id;
    String name;
    String size;

    public Vehicle(String id, String name, String size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public Vehicle() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }
}
