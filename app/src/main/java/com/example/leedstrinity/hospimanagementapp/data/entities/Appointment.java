package com.example.leedstrinity.hospimanagementapp.data.entities;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(
        tableName = "appointments",
        indices = {@Index(value = {"startTime", "clinicianId"})}
)
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String patientNhsNumber;
    public long startTime;
    public long endTime;
    public long clinicianId;
    public String clinicianName;
    public String clinic;
    public String status;

    // Required no-arg constructor for Room
    public Appointment() {}

    // Optional full constructor for manual use
    @Ignore
    public Appointment(String patientNhsNumber, long startTime, long endTime,
                       long clinicianId, String clinicianName, String clinic, String status) {
        this.patientNhsNumber = patientNhsNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.clinicianId = clinicianId;
        this.clinicianName = clinicianName;
        this.clinic = clinic;
        this.status = status;
    }
}




