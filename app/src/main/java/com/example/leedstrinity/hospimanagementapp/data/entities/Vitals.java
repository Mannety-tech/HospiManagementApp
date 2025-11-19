
package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "vitals")
public class Vitals {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String patientId;
    public double temperature;
    public int heartRate;
    public int systolicBP;
    public int diastolicBP;
    public int respiratoryRate;   // Breaths per minute
    public float oxygenSaturation;// %

    public long recordedAt;

    // Room will use this
    public Vitals() {}

    // Convenience constructor for manual creation
    @Ignore
    public Vitals(String patientId, double temperature, int heartRate, int systolicBP, int diastolicBP, int respiratoryRate, float oxygenSaturation, long recordedAt) {
        this.patientId = patientId;
        this.temperature = temperature;
        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.respiratoryRate = respiratoryRate;
        this.oxygenSaturation = oxygenSaturation;
        this.recordedAt = recordedAt;
    }
}



