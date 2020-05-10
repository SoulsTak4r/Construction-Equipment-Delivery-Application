package com.example.constructionequipmentapp;

public class Tasks {

    String Address;
    String Size;
    String Action;
    String Date;
    String Driver_ID;
    String Task_ID; // no need of edit text
    String Time;
    String Equipment;
    String TruckSize;
    String addedBy; // no need of edit text


    public Tasks(String A, String S, String AC, String date, String name, String task_ID, String time, String equipment, String truckSize, String added)
    {
        this.Address = A;
        this.Size = S;
        this.Action = AC;
        this.Date = date;
        this.Driver_ID = name;
        this.Task_ID = task_ID;
        this.Time = time;
        this.Equipment = equipment;
        this.TruckSize = truckSize;
        this.addedBy = added;
    }

    public Tasks()
    {

    }

    public String getTime()
    {
        return Time;

    }

    public String getEquipment()
    {
        return Equipment;

    }

    public String getTruckSize()
    {
        return TruckSize;

    }


    public String getAddress()
    {
        return Address;
    }

    public String getSize()
    {
        return Size;

    }

    public String getAction()
    {
        return Action;
    }

    public String getDate()
    {
        return Date;
    }

    public String getTask_ID()
    {
        return Task_ID;
    }

    public String getAddedBy()
    {
        return addedBy;

    }



    // --------------------------------------------
    public void setAddress(String Address)
    {
        this.Address = Address;
    }

    public void setSize(String Size)
    {
        this.Size = Size;

    }

    public void setTime(String time)
    {
        this.Time = time;

    }

    public void setEquipment(String equipment)
    {
        this.Equipment = equipment;

    }

    public void setTruckSize(String truckSize)
    {
        this.TruckSize  = truckSize;

    }


    public void setAction(String Action)
    {
        this.Action = Action;
    }

    public void setDate(String Date)
    {
        this.Date = Date;
    }

    public void setTask_ID(String Task_ID)
    {
        this.Task_ID = Task_ID;
    }

    public void setAddedBy(String added)
    {
        this.addedBy  = added;

    }


}
