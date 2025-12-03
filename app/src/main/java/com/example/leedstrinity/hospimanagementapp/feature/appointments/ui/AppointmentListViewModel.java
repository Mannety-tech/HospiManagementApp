package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;
import com.example.leedstrinity.hospimanagementapp.domain.GetTodaysAppointmentsUseCase;

import java.util.List;

public class AppointmentListViewModel extends AndroidViewModel {

    private final LiveData<List<Appointment>> allAppointments;
    private LiveData<List<Appointment>> todaysAppointments;

    private final GetTodaysAppointmentsUseCase getTodaysAppointmentsUseCase;

    public AppointmentListViewModel(@NonNull Application application) {
        super(application);

        // Initialize repository and DB
        AppDatabase db = AppDatabase.getInstance(application);
        AppointmentRepository repo = new AppointmentRepository(application);

        // Observe all appointments
        allAppointments = db.appointmentDao().getAllAppointments();

        // Initialize use case
        getTodaysAppointmentsUseCase = new GetTodaysAppointmentsUseCase(repo);
    }

    /** Expose all appointments in DB */
    public LiveData<List<Appointment>> getAllAppointments() {
        return allAppointments;
    }

    /** Expose today's appointments for a given clinic */
    public LiveData<List<Appointment>> getTodaysAppointments(String clinic) throws Exception {
        // Delegate to use case (which computes start/end internally)
        todaysAppointments = getTodaysAppointmentsUseCase.execute(clinic);
        return todaysAppointments;
    }
}




