package com.example.constructionequipmentapp;

public class User_class {
    String id;
    String key;
    String name;
    Task Tasks;

    public User_class(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public User_class() {
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }


}

