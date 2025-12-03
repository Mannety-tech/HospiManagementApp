package com.example.leedstrinity.hospimanagementapp.data.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.AppointmentDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.network.dto.ApiClient;
import com.example.leedstrinity.hospimanagementapp.network.dto.AppointmentDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class AppointmentRepository {

    private final AppointmentDao dao;
    private final ApiClient api;

    public AppointmentRepository(Context context) {
        this.dao = AppDatabase.getInstance(context).appointmentDao();
        this.api = new ApiClient(context);
    }

    /**
     * Fetch today's appointments from API, insert into local DB, and return appointments between start and end.
     */
    public LiveData<List<Appointment>> getTodaysAppointments(String clinic, long start, long end) throws Exception {
        Response<List<AppointmentDto>> response =
                api.appointmentApi().getTodaysAppointments(clinic).execute();

        List<Appointment> mapped = new ArrayList<>();

        if (response.isSuccessful() && response.body() != null) {
            for (AppointmentDto dto : response.body()) {
                mapped.add(map(dto));
            }
        }

        // Insert into local DB
        for (Appointment appointment : mapped) {
            dao.insert(appointment);
        }

        // Return reactive query
        return dao.findBetween(start, end);
    }

    /**
     * Book or reschedule an appointment via API and update local DB.
     */
    public Appointment bookOrReschedule(Appointment appt) throws Exception {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appt.getId());
        dto.setPatientName(appt.getPatientName());
        dto.setDate(appt.getDate());
        dto.setTime(appt.getTime());
        dto.setReason(appt.getReason());
        dto.setSpecialistName(appt.getSpecialistName());
        dto.setClinicLocation(appt.getClinicLocation());
        dto.setStartTime(appt.getStartTimeMillis());
        dto.setEndTime(appt.getEndTimeMillis());
        dto.setStatus(appt.getStatus());

        Response<AppointmentDto> response =
                api.appointmentApi().bookOrReschedule(dto).execute();

        if (response.isSuccessful() && response.body() != null) {
            Appointment saved = map(response.body());

            // Preserve ID if API didn’t return one
            if (saved.getId() == 0) {
                saved.setId(appt.getId());
            }

            if (appt.getId() == 0) {
                dao.insert(saved);
            } else {
                dao.update(saved);
            }

            return saved;
        } else {
            throw new IllegalStateException("Booking failed");
        }
    }

    /**
     * Detect overlapping appointments for a given specialist.
     */
    public LiveData<List<Appointment>> detectConflicts(String specialistName, long start, long end) {
        return dao.overlappingByName(specialistName, start, end);
    }

    /**
     * Map AppointmentDto (from API) into Appointment entity (for Room).
     */
    private Appointment map(AppointmentDto dto) {
        Appointment appointment = new Appointment();

        appointment.setPatientName(dto.getPatientName());
        appointment.setDate(dto.getDate());
        appointment.setTime(dto.getTime());
        appointment.setReason(dto.getReason());
        appointment.setSpecialistName(dto.getSpecialistName());
        appointment.setClinicLocation(dto.getClinicLocation());
        appointment.setStartTimeMillis(dto.getStartTime());
        appointment.setEndTimeMillis(dto.getEndTime());
        appointment.setStatus(dto.getStatus());

        if (dto.getId() != 0) {
            appointment.setId(dto.getId());
        }

        return appointment;
    }
}















