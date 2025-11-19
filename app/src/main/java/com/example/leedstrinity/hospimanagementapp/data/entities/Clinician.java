package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "clinicians")
public class Clinician {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "employee_number")
    public String employeeNumber;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "specialty")
    public String specialty;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "email")
    public String email;

    // Room will use this
    public Clinician() {}

    // Convenience constructor for manual creation
    @Ignore
    public Clinician(String employeeNumber, String fullName, String specialty, String phone, String email) {
        this.employeeNumber = employeeNumber;
        this.fullName = fullName;
        this.specialty = specialty;
        this.phone = phone;
        this.email = email;
    }
}


