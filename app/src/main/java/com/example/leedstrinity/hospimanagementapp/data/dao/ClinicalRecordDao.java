package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.leedstrinity.hospimanagementapp.data.entities.ClinicalRecord;

import java.util.List;

@Dao
public interface ClinicalRecordDao {

    // Insert a new clinical record
    @Insert
    void insert(ClinicalRecord clinicalRecord);

    // Update an existing record
    @Update
    void update(ClinicalRecord clinicalRecord);

    // Delete a record
    @Delete
    void delete(ClinicalRecord clinicalRecord);

    // Get all records for a patient (reactive LiveData version)
    @Query("SELECT * FROM clinical_records WHERE patientId = :patientId ORDER BY recordDate DESC")
    LiveData<List<ClinicalRecord>> findByPatientLive(String patientId);

    // Get all records for a patient (synchronous version)
    @Query("SELECT * FROM clinical_records WHERE patientId = :patientId ORDER BY recordDate DESC")
    List<ClinicalRecord> findByPatient(String patientId);

    // Get all records for a clinician
    @Query("SELECT * FROM clinical_records WHERE clinicianId = :clinicianId ORDER BY recordDate DESC")
    List<ClinicalRecord> findByClinician(String clinicianId);

    // Get records between two dates (assuming recordDate is stored as a long timestamp)
    @Query("SELECT * FROM clinical_records WHERE recordDate BETWEEN :start AND :end ORDER BY recordDate ASC")
    List<ClinicalRecord> findBetweenDates(long start, long end);

    // Get the latest record for a patient
    @Query("SELECT * FROM clinical_records WHERE patientId = :patientId ORDER BY recordDate DESC LIMIT 1")
    ClinicalRecord findLatestForPatient(String patientId);
}


