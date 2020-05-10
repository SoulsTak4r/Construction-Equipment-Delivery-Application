package com.example.constructionequipmentapp;

public class Task {
    String action;
    String addedBy;
    String address;
    String date;
    String equipment;
    String size;
    String task_ID;
    String time;
    String truckSize;

    public Task(String action, String addedBy, String address, String date, String equipment, String size, String task_ID, String time, String truckSize) {
        this.action = action;
        this.addedBy = addedBy;
        this.address = address;
        this.date = date;
        this.equipment = equipment;
        this.size = size;
        this.task_ID = task_ID;
        this.time = time;
        this.truckSize = truckSize;
    }

    public Task() {
    }

    public String getAction() {
        return action;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getSize() {
        return size;
    }

    public String getTask_ID() {
        return task_ID;
    }

    public String getTime() {
        return time;
    }

    public String getTruckSize() {
        return truckSize;
    }
}
