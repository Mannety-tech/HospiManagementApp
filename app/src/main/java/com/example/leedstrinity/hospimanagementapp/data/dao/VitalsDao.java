package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.util.Date;
import java.util.List;

@Dao
public interface VitalsDao {

    // Insert or replace if patientId already exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vitals vitals);

    @Update
    void update(Vitals vitals);

    // Get all vitals for a patient, newest first
    @Query("SELECT * FROM vitals WHERE patient_id = :patientId ORDER BY recorded_at DESC")
    List<Vitals> findByPatient(String patientId);

    // Get vitals between two dates
    @Query("SELECT * FROM vitals WHERE recorded_at BETWEEN :start AND :end ORDER BY recorded_at ASC")
    List<Vitals> findBetweenDates(Date start, Date end);

    // Get the latest vitals for a patient
    @Query("SELECT * FROM vitals WHERE patient_id = :patientId ORDER BY recorded_at DESC LIMIT 1")
    Vitals findLatestForPatient(String patientId);
}




