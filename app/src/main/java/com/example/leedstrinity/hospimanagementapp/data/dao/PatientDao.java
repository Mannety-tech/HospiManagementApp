package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void delete(Patient patient);

    // --- Fetch all patients synchronously ---
    @Query("SELECT * FROM patients")
    List<Patient> getAllPatientsSync();

    // --- Find patient by ID (sync) ---
    @Query("SELECT * FROM patients WHERE id = :patientId LIMIT 1")
    Patient findByIdSync(long patientId);

    // --- Find patient by ID (LiveData) ---
    @Query("SELECT * FROM patients WHERE id = :patientId LIMIT 1")
    LiveData<Patient> findByIdLive(long patientId);

    // --- Fetch all patients as LiveData ---
    @Query("SELECT * FROM patients ORDER BY name ASC")
    LiveData<List<Patient>> getAllPatients();

    // --- Delete all patients ---
    @Query("DELETE FROM patients")
    void deleteAll();

    // --- Login query ---
    @Query("SELECT * FROM patients WHERE email = :email AND password = :password LIMIT 1")
    Patient login(String email, String password);

    // --- Fetch the latest patient ---
    @Query("SELECT * FROM patients ORDER BY id DESC LIMIT 1")
    Patient getLatestPatient();
}

















