
package com.example.leedstrinity.hospimanagementapp.domain;

import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import java.util.Calendar;
import java.util.List;

public class GetTodaysAppointmentsUseCase {

    private final AppointmentRepository repo;

    //  Original constructor for production use
    public GetTodaysAppointmentsUseCase(Context context) {
        this.repo = new AppointmentRepository(context);
    }

    //  New constructor for testing (mock injection)
    public GetTodaysAppointmentsUseCase(AppointmentRepository repo) {
        this.repo = repo;
    }

    public List<Appointment> execute(String clinic) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        long end = cal.getTimeInMillis();
        return repo.getTodaysAppointments(clinic, start, end);
    }
}


