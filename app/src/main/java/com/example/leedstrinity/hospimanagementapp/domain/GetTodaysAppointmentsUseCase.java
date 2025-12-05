package com.example.leedstrinity.hospimanagementapp.domain;

import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import java.util.Calendar;
import java.util.List;

public class GetTodaysAppointmentsUseCase {

    private final AppointmentRepository repo;

    public GetTodaysAppointmentsUseCase(AppointmentRepository repo) {
        this.repo = repo;
    }

    public LiveData<List<Appointment>> execute(String clinic) {
        // Start of today (00:00:00)
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.HOUR_OF_DAY, 0);
        startCal.set(Calendar.MINUTE, 0);
        startCal.set(Calendar.SECOND, 0);
        startCal.set(Calendar.MILLISECOND, 0);
        long startMillis = startCal.getTimeInMillis();

        // End of today (23:59:59)
        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.HOUR_OF_DAY, 23);
        endCal.set(Calendar.MINUTE, 59);
        endCal.set(Calendar.SECOND, 59);
        endCal.set(Calendar.MILLISECOND, 999);
        long endMillis = endCal.getTimeInMillis();

        return repo.getTodaysAppointments(clinic, startMillis, endMillis);
    }
}




