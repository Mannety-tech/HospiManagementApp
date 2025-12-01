package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.leedstrinity.hospimanagementapp.data.entities.Clinic;

import java.util.List;

@Dao
public interface ClinicDao {

    // Insert a new clinic
    @Insert
    void insert(Clinic clinic);

    // Update an existing clinic
    @Update
    void update(Clinic clinic);

    // Delete a clinic
    @Delete
    void delete(Clinic clinic);

    //  Get all clinic names as LiveData
    @Query("SELECT name FROM clinic")
    LiveData<List<String>> getAllClinicNamesLive();

    // Optional: get all clinic entities
    @Query("SELECT * FROM clinic")
    LiveData<List<Clinic>> getAllClinics();
}

