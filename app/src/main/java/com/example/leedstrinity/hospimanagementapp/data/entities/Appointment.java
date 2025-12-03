package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "appointments")
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String patientName;
    private String date;
    private String time;
    private String reason;
    private String doctorName;
    private String clinicLocation;

    private long startTimeMillis;
    private long endTimeMillis;

    // --- Required no-arg constructor for Room ---
    public Appointment() { }

    // --- Convenience constructor (ignored by Room) ---
    @Ignore
    public Appointment(String patientName,
                       String date,
                       String time,
                       String reason,
                       String doctorName,
                       long startTimeMillis,
                       long endTimeMillis,
                       String clinicLocation) {
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.doctorName = doctorName;
        this.startTimeMillis = startTimeMillis;
        this.endTimeMillis = endTimeMillis;
        this.clinicLocation = clinicLocation;
    }

    // --- Getters and setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getClinicLocation() { return clinicLocation; }
    public void setClinicLocation(String clinicLocation) { this.clinicLocation = clinicLocation; }

    public long getStartTimeMillis() { return startTimeMillis; }
    public void setStartTimeMillis(long startTimeMillis) { this.startTimeMillis = startTimeMillis; }

    public long getEndTimeMillis() { return endTimeMillis; }
    public void setEndTimeMillis(long endTimeMillis) { this.endTimeMillis = endTimeMillis; }
}

















