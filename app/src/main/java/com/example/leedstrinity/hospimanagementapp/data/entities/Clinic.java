package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "clinic")
public class Clinic {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String address;

    // No-arg constructor (Room uses this)
    public Clinic() {}

    // Convenience constructor
    public Clinic(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}

