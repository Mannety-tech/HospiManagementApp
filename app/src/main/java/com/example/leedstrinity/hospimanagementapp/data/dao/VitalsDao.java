package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vitals vitals);


    @Update
    void update(Vitals vitals);


    @Query("SELECT * FROM vitals WHERE patient_id = :patientId ORDER BY recorded_at DESC")
    LiveData<List<Vitals>> findByPatient(String patientId);


    @Query("SELECT * FROM vitals WHERE recorded_at BETWEEN :start AND :end ORDER BY recorded_at ASC")
    LiveData<List<Vitals>> findBetweenDates(Date start, Date end);


    @Query("SELECT * FROM vitals WHERE patient_id = :patientId ORDER BY recorded_at DESC LIMIT 1")
    LiveData<Vitals> findLatestForPatient(String patientId);
}






