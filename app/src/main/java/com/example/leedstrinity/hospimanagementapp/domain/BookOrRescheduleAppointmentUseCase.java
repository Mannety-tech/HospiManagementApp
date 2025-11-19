
package com.example.leedstrinity.hospimanagementapp.domain;

import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

public class BookOrRescheduleAppointmentUseCase {

    private final AppointmentRepository repo;

    //  Constructor for production use
    public BookOrRescheduleAppointmentUseCase(Context context) {
        this.repo = new AppointmentRepository(context);
    }

    //  Constructor for testing (mock injection)
    public BookOrRescheduleAppointmentUseCase(AppointmentRepository repo) {
        this.repo = repo;
    }

    public Appointment execute(Appointment appointment) throws Exception {
        return repo.bookOrReschedule(appointment);
    }
}


