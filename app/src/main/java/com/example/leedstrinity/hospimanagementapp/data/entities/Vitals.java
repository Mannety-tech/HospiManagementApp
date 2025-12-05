package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Entity representing a patient's vital signs record.
 * Each record is tied to a patientId and includes timestamped measurements.
 */
@Entity(tableName = "vitals")
public class Vitals {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo(name = "patient_id")
    private String patientId;   // must always be set before insert

    @ColumnInfo(name = "heart_rate_bpm")
    private int heartRate;

    @ColumnInfo(name = "blood_pressure_systolic")
    private int systolicBP;

    @ColumnInfo(name = "blood_pressure_diastolic")
    private int diastolicBP;

    @ColumnInfo(name = "temperature_celsius")
    private double temperature;

    @ColumnInfo(name = "respiratory_rate_bpm")
    private int respiratoryRate;

    @ColumnInfo(name = "recorded_at")
    private Date recordedAt;

    // --- Required no-arg constructor for Room ---
    public Vitals() {
        this.recordedAt = new Date(); // default timestamp at creation
    }

    // --- Full constructor (explicit timestamp) ---
    @Ignore
    public Vitals(@NonNull String patientId,
                  int heartRate,
                  int systolicBP,
                  int diastolicBP,
                  double temperature,
                  int respiratoryRate,
                  @NonNull Date recordedAt) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.temperature = temperature;
        this.respiratoryRate = respiratoryRate;
        this.recordedAt = recordedAt;
    }

    // --- Convenience constructor (auto timestamp) ---
    @Ignore
    public Vitals(@NonNull String patientId,
                  int heartRate,
                  int systolicBP,
                  int diastolicBP,
                  double temperature,
                  int respiratoryRate) {
        this(patientId, heartRate, systolicBP, diastolicBP, temperature, respiratoryRate, new Date());
    }

    // --- Static factory method (forces patientId, auto timestamp) ---
    public static Vitals forPatient(@NonNull String patientId,
                                    int heartRate,
                                    int systolicBP,
                                    int diastolicBP,
                                    double temperature,
                                    int respiratoryRate) {
        return new Vitals(patientId, heartRate, systolicBP, diastolicBP, temperature, respiratoryRate);
    }

    // --- Getters and setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @NonNull
    public String getPatientId() { return patientId; }
    public void setPatientId(@NonNull String patientId) { this.patientId = patientId; }

    public int getHeartRate() { return heartRate; }
    public void setHeartRate(int heartRate) { this.heartRate = heartRate; }

    public int getSystolicBP() { return systolicBP; }
    public void setSystolicBP(int systolicBP) { this.systolicBP = systolicBP; }

    public int getDiastolicBP() { return diastolicBP; }
    public void setDiastolicBP(int diastolicBP) { this.diastolicBP = diastolicBP; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getRespiratoryRate() { return respiratoryRate; }
    public void setRespiratoryRate(int respiratoryRate) { this.respiratoryRate = respiratoryRate; }

    @NonNull
    public Date getRecordedAt() { return recordedAt; }
    public void setRecordedAt(@NonNull Date recordedAt) { this.recordedAt = recordedAt; }
}










