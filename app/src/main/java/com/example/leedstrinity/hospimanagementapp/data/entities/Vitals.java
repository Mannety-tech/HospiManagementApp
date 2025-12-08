package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.leedstrinity.hospimanagementapp.data.Converters;
import com.example.leedstrinity.hospimanagementapp.security.auth.SecureDatabaseHelper;

import java.text.DateFormat;
import java.util.Date;

@Entity(tableName = "vitals")
@TypeConverters({Converters.class})
public class Vitals {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long patientId;

    private String bloodPressure;   // e.g. "120/80"
    private String heartRate;       // e.g. "72"
    private String temperature;     // e.g. "36.8"
    private String oxygenLevel;     // e.g. "98%"

    private Date recordedAt;

    // --- Getters and setters ---
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getPatientId() { return patientId; }
    public void setPatientId(long patientId) { this.patientId = patientId; }

    public String getBloodPressure() { return SecureDatabaseHelper.decrypt(bloodPressure); }
    public void setBloodPressure(String bpPlainText) { this.bloodPressure = SecureDatabaseHelper.encrypt(bpPlainText); }

    public String getHeartRate() { return SecureDatabaseHelper.decrypt(heartRate); }
    public void setHeartRate(String hrPlainText) { this.heartRate = SecureDatabaseHelper.encrypt(hrPlainText); }

    public String getTemperature() { return SecureDatabaseHelper.decrypt(temperature); }
    public void setTemperature(String tempPlainText) { this.temperature = SecureDatabaseHelper.encrypt(tempPlainText); }

    public String getOxygenLevel() { return SecureDatabaseHelper.decrypt(oxygenLevel); }
    public void setOxygenLevel(String oxygenPlainText) { this.oxygenLevel = SecureDatabaseHelper.encrypt(oxygenPlainText); }

    public Date getRecordedAt() { return recordedAt; }
    public void setRecordedAt(Date recordedAt) { this.recordedAt = recordedAt; }

    // ✅ Helper method to format recordedAt nicely
    public String getFormattedRecordedAt() {
        if (recordedAt == null) return "";
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(recordedAt);
    }
}

















