package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.leedstrinity.hospimanagementapp.security.auth.SecureDatabaseHelper;

@Entity(tableName = "appointments")
public class Appointment {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long patientId;
    private String patientName;
    private String clinicianName;
    private String clinicLocation;

    // Store encrypted reason in DB
    private String reason;

    private String status;
    private String date;
    private long startTimeMillis;
    private long endTimeMillis;
    private String specialistName;

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

    // ✅ Automatically encrypt/decrypt reason
    public String getReason() {
        return SecureDatabaseHelper.decrypt(reason);
    }

    public void setReason(String reasonPlainText) {
        this.reason = SecureDatabaseHelper.encrypt(reasonPlainText);
    }

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

























