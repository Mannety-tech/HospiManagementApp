package com.example.leedstrinity.hospimanagementapp.data.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.AppointmentDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.network.dto.ApiClient;
import com.example.leedstrinity.hospimanagementapp.network.dto.AppointmentDto;

import java.io.IOException;
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

    public LiveData<List<Appointment>> getTodaysAppointments(String clinic, long start, long end) {
        List<Appointment> mapped = new ArrayList<>();

        try {
            Response<List<AppointmentDto>> response =
                    api.appointmentApi().getTodaysAppointments(clinic).execute();

            if (response.isSuccessful() && response.body() != null) {
                for (AppointmentDto dto : response.body()) {
                    mapped.add(map(dto));
                }
            }

            // Insert into local DB
            for (Appointment appointment : mapped) {
                dao.insert(appointment);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ✅ Use the LiveData version of the query
        return dao.findBetweenLive(start, end);
    }

    public Appointment bookOrReschedule(Appointment appt) {
        Appointment saved = null;

        try {
            AppointmentDto dto = new AppointmentDto();
            dto.setId(appt.getId());
            dto.setPatientName(appt.getPatientName());
            dto.setDate(appt.getDate());
            dto.setReason(appt.getReason());
            dto.setSpecialistName(appt.getSpecialistName());
            dto.setClinicLocation(appt.getClinicLocation());
            dto.setStartTime(appt.getStartTimeMillis());
            dto.setEndTime(appt.getEndTimeMillis());
            dto.setStatus(appt.getStatus());

            Response<AppointmentDto> response =
                    api.appointmentApi().bookOrReschedule(dto).execute();

            if (response.isSuccessful() && response.body() != null) {
                saved = map(response.body());

                if (saved.getId() == 0) {
                    saved.setId(appt.getId());
                }

                if (appt.getId() == 0) {
                    dao.insert(saved);
                } else {
                    dao.update(saved);
                }
            } else {
                throw new IllegalStateException("Booking failed");
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Network error while booking", e);
        }

        return saved;
    }

    public LiveData<List<Appointment>> detectConflicts(String specialistName, long start, long end) {
        // ✅ Use the LiveData version of overlapping query
        return dao.overlappingByNameLive(specialistName, start, end);
    }

    private Appointment map(AppointmentDto dto) {
        Appointment appointment = new Appointment();

        appointment.setPatientName(dto.getPatientName());
        appointment.setDate(dto.getDate());
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


















