package com.example.constructionequipmentapp;

public class Employee {
    String name;
    String id;

    public Employee(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public Employee(String name) {
        this.name = name;
    }
    public Employee(){}

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
