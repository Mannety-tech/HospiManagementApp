package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leedstrinity.hospimanagementapp.data.entities.Staff;

import java.util.List;

@Dao
public interface StaffDao {

    @Insert
    void insert(Staff staff);

    @Update
    void update(Staff staff);

    @Delete
    void delete(Staff staff);

    // Login query
    @Query("SELECT * FROM staff WHERE email = :email AND password = :password LIMIT 1")
    Staff login(String email, String password);

    // Get all staff (synchronous)
    @Query("SELECT * FROM staff")
    List<Staff> getAllStaff();

    // Get all staff full names (reactive LiveData for UI auto‑updates)
    @Query("SELECT firstName || ' ' || lastName FROM staff")
    LiveData<List<String>> getAllStaffFullNames();

    // Find staff by ID
    @Query("SELECT * FROM staff WHERE id = :staffId LIMIT 1")
    Staff findById(long staffId);

    // Get all doctors' full names (first + last)
    @Query("SELECT firstName || ' ' || lastName FROM staff WHERE specialty = 'Doctor'")
    List<String> getAllDoctorNames();

    // LiveData version
    @Query("SELECT firstName || ' ' || lastName FROM staff WHERE specialty = 'Doctor'")
    LiveData<List<String>> getAllDoctorNamesLive();

    // Get doctors by specialty (dynamic)
    @Query("SELECT firstName || ' ' || lastName FROM staff WHERE specialty = :specialty")
    LiveData<List<String>> getDoctorNamesBySpecialty(String specialty);

    // Get all distinct specialties
    @Query("SELECT DISTINCT specialty FROM staff")
    LiveData<List<String>> getAllSpecialtiesLive();

    // Get all specialists' full names
    @Query("SELECT firstName || ' ' || lastName FROM staff WHERE specialty IS NOT NULL")
    LiveData<List<String>> getAllSpecialistNamesLive();

    // Get clinics by specialty
    @Query("SELECT DISTINCT clinicLocation FROM staff WHERE specialty = :specialty")
    LiveData<List<String>> getClinicsBySpecialty(String specialty);

    @Query("SELECT * FROM staff")
    LiveData<List<Staff>> getAllStaffLive();

}











