package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "patients")
public class Patient {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String gender;
    private String dateOfBirth;
    private String contactNumber;
    private String address;

    // NHS number
    private String nhsNumber;

    // Login fields
    private String email;
    private String password;

    // Role: "patient" or "staff"
    private String role;

    // --- Constructor ---
    public Patient(String name,
                   String gender,
                   String dateOfBirth,
                   String contactNumber,
                   String address,
                   String nhsNumber,
                   String email,
                   String password,
                   String role) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.address = address;
        this.nhsNumber = nhsNumber;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // --- Getters & Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNhsNumber() { return nhsNumber; }
    public void setNhsNumber(String nhsNumber) { this.nhsNumber = nhsNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}









