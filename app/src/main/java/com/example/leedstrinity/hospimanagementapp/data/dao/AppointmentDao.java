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

    // -------------------------
    // Reactive (LiveData) queries
    // -------------------------

    // Get all appointments (ordered by start time)
    @Query("SELECT * FROM appointments ORDER BY startTimeMillis ASC")
    LiveData<List<Appointment>> getAllAppointments();

    // Get appointments by patient ID (reactive)
    @Query("SELECT * FROM appointments WHERE patientId = :patientId ORDER BY date DESC")
    LiveData<List<Appointment>> getAppointmentsForPatientLive(long patientId);

    // Get appointments by patient name (reactive)
    @Query("SELECT * FROM appointments WHERE patientName = :patientName ORDER BY startTimeMillis DESC")
    LiveData<List<Appointment>> getAppointmentsForPatientByNameLive(String patientName);

    // Get appointments for a clinic between two times (reactive)
    @Query("SELECT * FROM appointments WHERE clinicLocation = :clinic AND startTimeMillis BETWEEN :startMillis AND :endMillis")
    LiveData<List<Appointment>> getAppointmentsForClinicBetweenLive(String clinic, long startMillis, long endMillis);

    // Get appointments by status (reactive)
    @Query("SELECT * FROM appointments WHERE status = :status ORDER BY startTimeMillis DESC")
    LiveData<List<Appointment>> getAppointmentsByStatusLive(String status);

    // Get appointments between two times (reactive)
    @Query("SELECT * FROM appointments WHERE startTimeMillis >= :start AND endTimeMillis <= :end ORDER BY startTimeMillis ASC")
    LiveData<List<Appointment>> findBetweenLive(long start, long end);

    // Detect overlapping appointments for a given specialist (reactive)
    @Query("SELECT * FROM appointments " +
            "WHERE specialistName = :specialistName " +
            "AND ((startTimeMillis BETWEEN :start AND :end) " +
            "OR (endTimeMillis BETWEEN :start AND :end) " +
            "OR (:start BETWEEN startTimeMillis AND endTimeMillis) " +
            "OR (:end BETWEEN startTimeMillis AND endTimeMillis))")
    LiveData<List<Appointment>> overlappingByNameLive(String specialistName, long start, long end);

    // -------------------------
    // Synchronous queries (plain List or single Appointment)
    // -------------------------

    // Get all appointments (blocking)
    @Query("SELECT * FROM appointments ORDER BY startTimeMillis ASC")
    List<Appointment> getAllAppointmentsSync();

    // Get appointments by patient ID (blocking)
    @Query("SELECT * FROM appointments WHERE patientId = :patientId ORDER BY date DESC")
    List<Appointment> getAppointmentsForPatientSync(long patientId);

    // Get latest appointment for a patient (blocking)
    @Query("SELECT * FROM appointments WHERE patientId = :patientId ORDER BY date DESC LIMIT 1")
    Appointment getLatestAppointmentForPatientSync(long patientId);

    // Get appointments by patient name (blocking)
    @Query("SELECT * FROM appointments WHERE patientName = :patientName ORDER BY startTimeMillis DESC")
    List<Appointment> getAppointmentsForPatientByNameSync(String patientName);

    // Get appointments for a clinic between two times (blocking)
    @Query("SELECT * FROM appointments WHERE clinicLocation = :clinic AND startTimeMillis BETWEEN :startMillis AND :endMillis")
    List<Appointment> getAppointmentsForClinicBetweenSync(String clinic, long startMillis, long endMillis);

    // Get appointments by status (blocking)
    @Query("SELECT * FROM appointments WHERE status = :status ORDER BY startTimeMillis DESC")
    List<Appointment> getAppointmentsByStatusSync(String status);

    // Get appointments between two times (blocking)
    @Query("SELECT * FROM appointments WHERE startTimeMillis >= :start AND endTimeMillis <= :end ORDER BY startTimeMillis ASC")
    List<Appointment> findBetweenSync(long start, long end);

    // Detect overlapping appointments for a given specialist (blocking)
    @Query("SELECT * FROM appointments " +
            "WHERE specialistName = :specialistName " +
            "AND ((startTimeMillis BETWEEN :start AND :end) " +
            "OR (endTimeMillis BETWEEN :start AND :end) " +
            "OR (:start BETWEEN startTimeMillis AND endTimeMillis) " +
            "OR (:end BETWEEN startTimeMillis AND endTimeMillis))")
    List<Appointment> overlappingByNameSync(String specialistName, long start, long end);
}
















