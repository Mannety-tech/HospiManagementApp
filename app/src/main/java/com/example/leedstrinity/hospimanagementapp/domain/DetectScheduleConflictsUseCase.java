package com.example.leedstrinity.hospimanagementapp.domain;


import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import java.util.List;

public class
DetectScheduleConflictsUseCase {

    protected AppointmentRepository repo;


    public DetectScheduleConflictsUseCase(Context context) {
        this.repo = new AppointmentRepository(context);
    }

    public boolean hasConflict(long clinicianId, long start, long end) {
        List<Appointment> overlaps = repo.detectConflicts(clinicianId, start, end);
        return overlaps != null && !overlaps.isEmpty();
    }
}

