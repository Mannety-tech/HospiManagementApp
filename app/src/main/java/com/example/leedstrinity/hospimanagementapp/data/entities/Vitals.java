package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

import java.util.Date;

@Entity(tableName = "vitals")
public class Vitals {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "patient_id", index = true)
    private int patientId;

    private int heartRate;
    private int systolicBP;
    private int diastolicBP;
    private double temperature;
    private int respiratoryRate;

    @ColumnInfo(name = "recorded_at")
    private Date recordedAt;

    // --- Getters and setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

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

    public Date getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Date recordedAt) { this.recordedAt = recordedAt; }

    // --- Factory method ---
    public static Vitals forPatient(int patientId,
                                    int heartRate,
                                    int systolicBP,
                                    int diastolicBP,
                                    double temperature,
                                    int respiratoryRate) {
        Vitals vitals = new Vitals();
        vitals.setPatientId(patientId);
        vitals.setHeartRate(heartRate);
        vitals.setSystolicBP(systolicBP);
        vitals.setDiastolicBP(diastolicBP);
        vitals.setTemperature(temperature);
        vitals.setRespiratoryRate(respiratoryRate);
        vitals.setRecordedAt(new Date()); // capture current time
        return vitals;
    }
}












