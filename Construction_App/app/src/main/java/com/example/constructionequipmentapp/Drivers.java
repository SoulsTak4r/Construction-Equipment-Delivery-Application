package com.example.constructionequipmentapp;

public class Drivers {

    String Name;
    String ID;
    private  String  key;


    public Drivers(String n, String i, String k)
    {
        this.Name = n;
        this.ID = i;
        this.key = k;
    }

    public Drivers()
    {

    }

    public String getKey()
    {
        return key;
    }

    public void setName(String name)
    {
        this.Name = name;
    }

    public void setID(String id)
    {
        this.ID = id;
    }

    public String getName()
    {
        return Name;
    }

    public String getId()
    {
        return ID;
    }

}
