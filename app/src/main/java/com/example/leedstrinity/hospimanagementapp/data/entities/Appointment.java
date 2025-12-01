package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "appointments")
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String patientName;
    private String patientNhsNumber;

    private String date;
    private String time;
    private String reason;
    private String doctorName;
    private long start;
    private long end;
    private String status;

    private String clinicLocation;

    // No‑arg constructor (Room uses this)
    public Appointment() {
    }

    // Convenience constructor (ignored by Room)
    @Ignore
    public Appointment(String patientName,
                       String date,
                       String time,
                       String reason,
                       String doctorName,
                       long start,
                       long end,
                       String clinicLocation) {
        this.patientName = patientName;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.doctorName = doctorName;
        this.start = start;
        this.end = end;
        this.clinicLocation = clinicLocation;
    }

    // Expanded convenience constructor (ignored by Room)
    @Ignore
    public Appointment(String patientName,
                       String patientNhsNumber,
                       String date,
                       String time,
                       String reason,
                       String doctorName,
                       long start,
                       long end,
                       String status,
                       String clinicLocation) {
        this.patientName = patientName;
        this.patientNhsNumber = patientNhsNumber;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.doctorName = doctorName;
        this.start = start;
        this.end = end;
        this.status = status;
        this.clinicLocation = clinicLocation;
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getPatientNhsNumber() { return patientNhsNumber; }
    public void setPatientNhsNumber(String patientNhsNumber) { this.patientNhsNumber = patientNhsNumber; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public long getStart() { return start; }
    public void setStart(long start) { this.start = start; }

    public long getEnd() { return end; }
    public void setEnd(long end) { this.end = end; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getClinicLocation() { return clinicLocation; }
    public void setClinicLocation(String clinicLocation) { this.clinicLocation = clinicLocation; }
}
















