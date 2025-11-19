package com.example.leedstrinity.hospimanagementapp.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Index;
import androidx.room.Ignore;

@Entity(
        tableName = "clinical_records",
        indices = {@Index(value = {"patientId", "clinicianId"})}
)
public class ClinicalRecord {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long patientId;        // Link to Patient entity
    public long clinicianId;      // Link to Clinician entity
    public long recordDate;       // Epoch millis for date/time
    public String diagnosis;
    public String treatmentPlan;
    public String notes;

    // Room will use this
    public ClinicalRecord() {}

    // Convenience constructor for manual creation
    @Ignore
    public ClinicalRecord(long patientId, long clinicianId, long recordDate,
                          String diagnosis, String treatmentPlan, String notes) {
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.recordDate = recordDate;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
        this.notes = notes;
    }
}







