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

    // Reactive queries
    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY recordedAt DESC")
    LiveData<List<Vitals>> getVitalsForPatientLive(long patientId);

    @Query("SELECT * FROM vitals WHERE recordedAt BETWEEN :start AND :end ORDER BY recordedAt ASC")
    LiveData<List<Vitals>> findBetweenDatesLive(Date start, Date end);

    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY recordedAt DESC LIMIT 1")
    LiveData<Vitals> findLatestForPatientLive(long patientId);

    // Synchronous queries
    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY recordedAt DESC")
    List<Vitals> getVitalsForPatientSync(long patientId);

    @Query("SELECT * FROM vitals WHERE recordedAt BETWEEN :start AND :end ORDER BY recordedAt ASC")
    List<Vitals> findBetweenDatesSync(Date start, Date end);

    @Query("SELECT * FROM vitals WHERE patientId = :patientId ORDER BY recordedAt DESC LIMIT 1")
    Vitals findLatestForPatientSync(long patientId);
}











