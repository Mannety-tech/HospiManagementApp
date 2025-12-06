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

    // --- Login query ---
    @Query("SELECT * FROM staff WHERE email = :email AND password = :password LIMIT 1")
    Staff login(String email, String password);

    // --- Get all staff (synchronous) ---
    @Query("SELECT * FROM staff")
    List<Staff> getAllStaff();

    // --- Get all staff full names ---
    @Query("SELECT first_name || ' ' || last_name FROM staff")
    LiveData<List<String>> getAllStaffFullNames();

    // --- Find staff by ID ---
    @Query("SELECT * FROM staff WHERE id = :staffId LIMIT 1")
    Staff findById(long staffId);

    // --- Get all distinct specialties (for signup spinner) ---
    @Query("SELECT DISTINCT specialty FROM staff WHERE specialty IS NOT NULL AND specialty != ''")
    LiveData<List<String>> getAllSpecialtiesLive();

    // --- Get clinics by specialty (for signup spinner) ---
    @Query("SELECT DISTINCT clinic_location FROM staff WHERE specialty = :specialty AND clinic_location IS NOT NULL AND clinic_location != ''")
    LiveData<List<String>> getClinicsBySpecialty(String specialty);

    // --- Get all staff as LiveData ---
    @Query("SELECT * FROM staff")
    LiveData<List<Staff>> getAllStaffLive();

    // --- Optional: get all specialists' names ---
    @Query("SELECT first_name || ' ' || last_name FROM staff WHERE specialty IS NOT NULL AND specialty != ''")
    LiveData<List<String>> getAllSpecialistNamesLive();
}













