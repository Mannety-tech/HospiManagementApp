package com.example.leedstrinity.hospimanagementapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

import java.util.List;

@Dao
public interface AppointmentDao {

    // Insert a new appointment
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Appointment appointment);

    // Update an existing appointment
    @Update
    void update(Appointment appointment);

    // Get all appointments (ordered by start time)
    @Query("SELECT * FROM appointments ORDER BY startTimeMillis ASC")
    LiveData<List<Appointment>> getAllAppointments();

    // Get appointments by patient name
    @Query("SELECT * FROM appointments WHERE patientName = :patientName ORDER BY startTimeMillis DESC")
    LiveData<List<Appointment>> getAppointmentsForPatient(String patientName);

    // Get appointments for a clinic between two times
    @Query("SELECT * FROM appointments WHERE clinicLocation = :clinic AND startTimeMillis BETWEEN :startMillis AND :endMillis")
    LiveData<List<Appointment>> getAppointmentsForClinicBetween(String clinic, long startMillis, long endMillis);

    // Get appointments by status
    @Query("SELECT * FROM appointments WHERE status = :status ORDER BY startTimeMillis DESC")
    LiveData<List<Appointment>> getAppointmentsByStatus(String status);

    // Get appointments between two times (reactive)
    @Query("SELECT * FROM appointments " +
            "WHERE startTimeMillis >= :start AND endTimeMillis <= :end " +
            "ORDER BY startTimeMillis ASC")
    LiveData<List<Appointment>> findBetween(long start, long end);

    // Detect overlapping appointments for a given specialist
    @Query("SELECT * FROM appointments " +
            "WHERE specialistName = :specialistName " +
            "AND ((startTimeMillis BETWEEN :start AND :end) " +
            "OR (endTimeMillis BETWEEN :start AND :end) " +
            "OR (:start BETWEEN startTimeMillis AND endTimeMillis) " +
            "OR (:end BETWEEN startTimeMillis AND endTimeMillis))")
    LiveData<List<Appointment>> overlappingByName(String specialistName, long start, long end);

    // Optional synchronous version if you need blocking calls in repository
    @Query("SELECT * FROM appointments " +
            "WHERE startTimeMillis >= :start AND endTimeMillis <= :end " +
            "ORDER BY startTimeMillis ASC")
    List<Appointment> findBetweenSync(long start, long end);
}












