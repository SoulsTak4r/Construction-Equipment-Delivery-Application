package com.example.constructionequipmentapp;

public class Managers {
    String Name;
    String ID;
    String key;


    public Managers(String name, String id, String k)
    {
        this.Name = name;
        this.ID = id;
        this.key = k;
    }
    public Managers()
    {

    }


    public String getName()
    {
        return Name;
    }

    public String getID()
    {
        return ID;
    }

    public String getKey()
    {
        return key;
    }


}
