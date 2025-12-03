package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

import java.util.List;

public class AppointmentListViewModel extends AndroidViewModel {

    private final LiveData<List<Appointment>> allAppointments;

    public AppointmentListViewModel(@NonNull Application application) {
        super(application);
        // Get DB instance and observe all appointments
        AppDatabase db = AppDatabase.getInstance(application);
        allAppointments = db.appointmentDao().getAllAppointmentsLive();
    }

    public LiveData<List<Appointment>> getAllAppointments() {
        return allAppointments;
    }
}

