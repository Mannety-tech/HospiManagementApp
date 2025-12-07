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
    @Query("SELECT * FROM appointment ORDER BY start_time ASC")
    LiveData<List<Appointment>> getAllAppointments();

    // Get appointments by patient ID
    @Query("SELECT * FROM appointment WHERE patient_id = :patientId ORDER BY date DESC")
    LiveData<List<Appointment>> getAppointmentsForPatient(long patientId);

    // Get appointments by patient name
    @Query("SELECT * FROM appointment WHERE patient_name = :patientName ORDER BY start_time DESC")
    LiveData<List<Appointment>> getAppointmentsForPatient(String patientName);

    // Get appointments for a clinic between two times
    @Query("SELECT * FROM appointment WHERE clinic_location = :clinic AND start_time BETWEEN :startMillis AND :endMillis")
    LiveData<List<Appointment>> getAppointmentsForClinicBetween(String clinic, long startMillis, long endMillis);

    // Get appointments by status
    @Query("SELECT * FROM appointment WHERE status = :status ORDER BY start_time DESC")
    LiveData<List<Appointment>> getAppointmentsByStatus(String status);

    // Get appointments between two times (reactive)
    @Query("SELECT * FROM appointment " +
            "WHERE start_time >= :start AND end_time <= :end " +
            "ORDER BY start_time ASC")
    LiveData<List<Appointment>> findBetween(long start, long end);

    // Detect overlapping appointments for a given specialist
    @Query("SELECT * FROM appointment " +
            "WHERE specialist_name = :specialistName " +
            "AND ((start_time BETWEEN :start AND :end) " +
            "OR (end_time BETWEEN :start AND :end) " +
            "OR (:start BETWEEN start_time AND end_time) " +
            "OR (:end BETWEEN start_time AND end_time))")
    LiveData<List<Appointment>> overlappingByName(String specialistName, long start, long end);

    // Optional synchronous version if you need blocking calls in repository
    @Query("SELECT * FROM appointment " +
            "WHERE start_time >= :start AND end_time <= :end " +
            "ORDER BY start_time ASC")
    List<Appointment> findBetweenSync(long start, long end);
}














