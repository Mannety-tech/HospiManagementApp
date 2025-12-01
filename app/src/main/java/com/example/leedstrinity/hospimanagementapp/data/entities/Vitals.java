package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "vitals")
public class Vitals {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @NonNull
    @ColumnInfo(name = "patient_id")
    public String patientId;

    @ColumnInfo(name = "heart_rate_bpm")
    public int heartRate;

    @ColumnInfo(name = "blood_pressure_systolic")
    public int systolicBP;

    @ColumnInfo(name = "blood_pressure_diastolic")
    public int diastolicBP;

    @ColumnInfo(name = "temperature_celsius")
    public double temperature;

    @ColumnInfo(name = "respiratory_rate_bpm")
    public int respiratoryRate;

    @ColumnInfo(name = "recorded_at")
    public Date recordedAt;

    // Default constructor for Room
    public Vitals() {}

    // Full constructor (explicit timestamp)
    @Ignore
    public Vitals(@NonNull String patientId,
                  int heartRate,
                  int systolicBP,
                  int diastolicBP,
                  double temperature,
                  int respiratoryRate,
                  Date recordedAt) {
        this.patientId = patientId;
        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.temperature = temperature;
        this.respiratoryRate = respiratoryRate;
        this.recordedAt = recordedAt;
    }

    // Convenience constructor (auto timestamp)
    @Ignore
    public Vitals(@NonNull String patientId,
                  int heartRate,
                  int systolicBP,
                  int diastolicBP,
                  double temperature,
                  int respiratoryRate) {
        this(patientId, heartRate, systolicBP, diastolicBP, temperature, respiratoryRate, new Date());
    }
}





