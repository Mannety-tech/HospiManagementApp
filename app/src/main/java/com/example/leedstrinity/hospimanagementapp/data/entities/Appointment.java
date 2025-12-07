package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "appointment")
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "patient_name")
    private String patientName;

    @ColumnInfo(name = "clinician_name")
    private String clinicianName;

    @ColumnInfo(name = "clinic_location")
    private String clinicLocation;

    @ColumnInfo(name = "reason")
    private String reason;

    @ColumnInfo(name = "status")
    private String status; // e.g. BOOKED, COMPLETED, CANCELLED

    @ColumnInfo(name = "date")
    private String date;   // e.g. "06/12/2025"

    @ColumnInfo(name = "start_time")
    private long startTimeMillis;

    @ColumnInfo(name = "end_time")
    private long endTimeMillis;

    @ColumnInfo(name = "specialist_name")
    private String specialistName;

    // --- Default constructor ---
    public Appointment() {}

    // --- Full constructor (with patientId) ---
    public Appointment(long patientId,
                       String patientName,
                       String clinicianName,
                       String clinicLocation,
                       String reason,
                       String status,
                       String date,
                       long startTimeMillis,
                       long endTimeMillis,
                       String specialistName) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.clinicianName = clinicianName;
        this.clinicLocation = clinicLocation;
        this.reason = reason;
        this.status = status;
        this.date = date;
        this.startTimeMillis = startTimeMillis;
        this.endTimeMillis = endTimeMillis;
        this.specialistName = specialistName;
    }

    // --- Convenience constructor (for UI entry without patientId) ---
    public Appointment(String patientName,
                       String date,
                       String time, // optional raw time string from UI
                       String reason,
                       String specialistName,
                       long startTimeMillis,
                       long endTimeMillis,
                       String clinicLocation,
                       String status) {
        this.patientName = patientName;
        this.date = date;
        this.reason = reason;
        this.specialistName = specialistName;
        this.startTimeMillis = startTimeMillis;
        this.endTimeMillis = endTimeMillis;
        this.clinicLocation = clinicLocation;
        this.status = status;
        // `time` can be stored separately if needed, or ignored if you rely on millis
    }

    // --- Getters and setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPatientId() { return patientId; }
    public void setPatientId(long patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getClinicianName() { return clinicianName; }
    public void setClinicianName(String clinicianName) { this.clinicianName = clinicianName; }

    public String getClinicLocation() { return clinicLocation; }
    public void setClinicLocation(String clinicLocation) { this.clinicLocation = clinicLocation; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public long getStartTimeMillis() { return startTimeMillis; }
    public void setStartTimeMillis(long startTimeMillis) { this.startTimeMillis = startTimeMillis; }

    public long getEndTimeMillis() { return endTimeMillis; }
    public void setEndTimeMillis(long endTimeMillis) { this.endTimeMillis = endTimeMillis; }

    public String getSpecialistName() { return specialistName; }
    public void setSpecialistName(String specialistName) { this.specialistName = specialistName; }
}






















