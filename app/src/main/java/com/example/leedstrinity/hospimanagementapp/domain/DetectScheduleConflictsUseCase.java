package com.example.leedstrinity.hospimanagementapp.domain;

import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import java.util.List;

public class DetectScheduleConflictsUseCase {

    private final AppointmentRepository repo;

    public DetectScheduleConflictsUseCase(AppointmentRepository repo) {
        this.repo = repo;
    }

    public LiveData<List<Appointment>> execute(String specialistName, long start, long end) {
        return repo.detectConflicts(specialistName, start, end);
    }
}


