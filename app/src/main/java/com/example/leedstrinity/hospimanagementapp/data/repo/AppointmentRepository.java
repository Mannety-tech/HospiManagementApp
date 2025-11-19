package com.example.leedstrinity.hospimanagementapp.data.repo;


import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.data.AppDatabase;
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

    public List<Appointment> getTodaysAppointments(String clinic, long start, long end) throws Exception {
        // Fetch from mock network
        Response<List<AppointmentDto>> response = api.appointmentApi().getTodaysAppointments(clinic).execute();
        List<Appointment> mapped = new ArrayList<>();

        if (response.isSuccessful() && response.body() != null) {
            for (AppointmentDto dto : response.body()) {
                mapped.add(map(dto));
            }
        }

        // Cache to DB (simplified: insert if none today)
        for (Appointment appointment : mapped) {
            dao.insert(appointment);
        }

        // Return from DB (source of truth)
        return dao.findBetween(start, end);
    }

    public Appointment bookOrReschedule(Appointment appt) throws Exception {
        AppointmentDto dto = new AppointmentDto();
        dto.id = appt.id;
        dto.patientNhsNumber = appt.patientNhsNumber;
        dto.startTime = appt.startTime;
        dto.endTime = appt.endTime;
        dto.clinicianId = appt.clinicianId;
        dto.clinicianName = appt.clinicianName;
        dto.clinic = appt.clinic;
        dto.status = "BOOKED";

        Response<AppointmentDto> response = api.appointmentApi().bookOrReschedule(dto).execute();

        if (response.isSuccessful() && response.body() != null) {
            Appointment saved = map(response.body());

            if (saved.id == 0) {
                saved.id = appt.id; // Preserve local ID if mock returns 0
            }

            if (appt.id == 0) {
                dao.insert(saved);
            } else {
                dao.update(saved);
            }

            return saved;
        } else {
            throw new IllegalStateException("Booking failed");
        }
    }

    public List<Appointment> detectConflicts(long clinicianId, long start, long end) {
        return dao.overlapping(clinicianId, start, end);
    }

    private Appointment map(AppointmentDto dto) {
        Appointment appointment = new Appointment();
        appointment.id = dto.id;
        appointment.patientNhsNumber = dto.patientNhsNumber;
        appointment.startTime = dto.startTime;
        appointment.endTime = dto.endTime;
        appointment.clinicianId = dto.clinicianId;
        appointment.clinicianName = dto.clinicianName;
        appointment.clinic = dto.clinic;
        appointment.status = dto.status;
        return appointment;
    }
}

