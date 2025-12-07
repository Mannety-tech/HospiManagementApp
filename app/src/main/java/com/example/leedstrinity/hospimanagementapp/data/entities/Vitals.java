package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.util.Date;

@Entity(tableName = "vitals")
public class Vitals {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "patient_id")
    private long patientId;

    @ColumnInfo(name = "blood_pressure")
    private String bloodPressure;

    @ColumnInfo(name = "heart_rate")
    private int heartRate;

    @ColumnInfo(name = "temperature")
    private double temperature;

    @ColumnInfo(name = "oxygen_level")
    private int oxygenLevel;

    @ColumnInfo(name = "recorded_at")
    private Date recordedAt;

    // --- Constructors ---
    public Vitals() {}

    public Vitals(long patientId,
                  String bloodPressure,
                  int heartRate,
                  double temperature,
                  int oxygenLevel,
                  Date recordedAt) {
        this.patientId = patientId;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
        this.recordedAt = recordedAt;
    }

    // --- Static factory method ---
    public static Vitals forPatient(long patientId,
                                    int systolic,
                                    int diastolic,
                                    int heartRate,
                                    double temperature,
                                    int oxygenLevel) {
        String bp = systolic + "/" + diastolic;
        Date now = new Date();
        return new Vitals(patientId, bp, heartRate, temperature, oxygenLevel, now);
    }

    // --- Getters and setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPatientId() { return patientId; }
    public void setPatientId(long patientId) { this.patientId = patientId; }

    public String getBloodPressure() { return bloodPressure; }
    public void setBloodPressure(String bloodPressure) { this.bloodPressure = bloodPressure; }

    public int getHeartRate() { return heartRate; }
    public void setHeartRate(int heartRate) { this.heartRate = heartRate; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getOxygenLevel() { return oxygenLevel; }
    public void setOxygenLevel(int oxygenLevel) { this.oxygenLevel = oxygenLevel; }

    public Date getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Date recordedAt) { this.recordedAt = recordedAt; }
}














