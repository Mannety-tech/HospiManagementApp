package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;

import java.util.List;

@Dao
public interface PatientDao {

    @Insert
    void insertPatient(Patient patient);

    @Query("SELECT * FROM patients WHERE email = :email AND password = :password LIMIT 1")
    Patient login(String email, String password);

    // Debug helper
    @Query("SELECT * FROM patients")
    List<Patient> getAllPatients();
}










