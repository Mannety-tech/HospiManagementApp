package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;

import java.util.List;



@Dao
public interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient patient);

    @Update
    void update(Patient patient);

    @Query("SELECT * FROM patients ORDER BY name ASC")
    LiveData<List<Patient>> getAllPatients();

    @Query("SELECT * FROM patients WHERE id = :id LIMIT 1")
    LiveData<Patient> findPatientById(int id);

    @Query("DELETE FROM patients")
    void deleteAll();

    @Query("SELECT * FROM patients WHERE email = :email AND password = :password LIMIT 1")
    Patient login(String email, String password);
}













