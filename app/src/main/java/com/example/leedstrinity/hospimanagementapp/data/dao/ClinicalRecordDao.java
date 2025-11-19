package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leedstrinity.hospimanagementapp.data.entities.ClinicalRecord;

import java.util.List;

@Dao
public interface ClinicalRecordDao {

    @Insert
    void insert(ClinicalRecord clinicalRecord);

    @Update
    void update(ClinicalRecord clinicalRecord);

    @Query("SELECT * FROM clinical_records WHERE patientId = :patientId")
    List<ClinicalRecord> findByPatient(long patientId);

    @Query("SELECT * FROM clinical_records WHERE clinicianId = :clinicianId")
    List<ClinicalRecord> findByClinician(long clinicianId);

    @Query("SELECT * FROM clinical_records WHERE recordDate BETWEEN :start AND :end")
    List<ClinicalRecord> findBetweenDates(long start, long end);
}
