package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.util.List;

@Dao
public interface VitalsDao {

    @Insert
    void insert(Vitals vitals);

    @Update
    void update(Vitals vitals);

    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY recordedAt DESC")
    List<Vitals> findByPatient(long patientId);

    @Query("SELECT * FROM vitals WHERE recordedAt BETWEEN :start AND :end")
    List<Vitals> findBetweenDates(long start, long end);

    @Query("SELECT * FROM vitals WHERE patientId = :patientId AND recordedAt = :timestamp LIMIT 1")
    Vitals findSpecificRecord(long patientId, long timestamp);
}


