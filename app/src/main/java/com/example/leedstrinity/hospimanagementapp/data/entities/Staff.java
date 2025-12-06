package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import androidx.room.ColumnInfo;

@Entity(tableName = "staff")
public class Staff {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "employee_number")
    private String employeeNumber;

    @ColumnInfo(name = "specialty")
    private String specialty;

    @ColumnInfo(name = "role")
    private String role;

    @ColumnInfo(name = "clinic_location")
    private String clinicLocation;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    // --- Constructors ---
    public Staff() {
        // Room requires a no-arg constructor
    }

    @Ignore
    public Staff(String firstName,
                 String lastName,
                 String employeeNumber,
                 String specialty,
                 String role,
                 String clinicLocation,
                 String phone,
                 String email,
                 String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeNumber = employeeNumber;
        this.specialty = specialty;
        this.role = role;
        this.clinicLocation = clinicLocation;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    // --- Getters and setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getClinicLocation() { return clinicLocation; }
    public void setClinicLocation(String clinicLocation) { this.clinicLocation = clinicLocation; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() {
        return firstName + " " + lastName;
    }
}




