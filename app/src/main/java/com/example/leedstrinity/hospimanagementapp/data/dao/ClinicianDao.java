package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leedstrinity.hospimanagementapp.data.entities.Clinician;

import java.util.List;

@Dao
public interface ClinicianDao {

    // Create
    @Insert
    void insert(Clinician clinician);

    // Read (by specialty)
    @Query("SELECT * FROM clinicians WHERE specialty = :specialty")
    LiveData<List<Clinician>> getClinicianBySpecialty(String specialty);

    // Read (all clinicians, reactive)
    @Query("SELECT * FROM clinicians ORDER BY full_name ASC")
    LiveData<List<Clinician>> getAllStaff();

    // Read (all clinicians, synchronous snapshot)
    @Query("SELECT * FROM clinicians ORDER BY full_name ASC")
    List<Clinician> getAllClinicians();

    // Update
    @Update
    void update(Clinician clinician);

    // Delete
    @Delete
    void delete(Clinician clinician);


    @Insert
    void insertClinician(Clinician clinician);
}


