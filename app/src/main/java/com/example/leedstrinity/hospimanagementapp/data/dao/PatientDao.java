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

    // --- Fetch all patients synchronously (for background threads) ---
    @Query("SELECT * FROM patients")
    List<Patient> getAllPatientsSync();

    @Query("SELECT * FROM patients WHERE id = :patientId LIMIT 1")
    Patient findById(long patientId);

    // --- Fetch all patients as LiveData (for UI observation) ---
    @Query("SELECT * FROM patients ORDER BY name ASC")
    LiveData<List<Patient>> getAllPatients();

    // --- Find patient by ID ---
    @Query("SELECT * FROM patients WHERE id = :id LIMIT 1")
    LiveData<Patient> findPatientById(long id);

    // --- Delete all patients ---
    @Query("DELETE FROM patients")
    void deleteAll();

    // --- Login query ---
    @Query("SELECT * FROM patients WHERE email = :email AND password = :password LIMIT 1")
    Patient login(String email, String password);

    // --- Fetch the latest patient directly ---
    @Query("SELECT * FROM patients ORDER BY id DESC LIMIT 1")
    Patient getLatestPatient();
}
















