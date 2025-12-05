package com.example.leedstrinity.hospimanagementapp.domain;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;
import java.util.List;

public class DetectScheduleConflictsUseCase {

    protected AppointmentRepository repo;

    // Production constructor: builds repository from database
    public DetectScheduleConflictsUseCase(Context context) {
        this.repo = new AppointmentRepository(context);
    }

    // Test constructor: allows injecting a mock repo directly
    public DetectScheduleConflictsUseCase(AppointmentRepository repo) {
        this.repo = repo;
    }

    public boolean hasConflict(String doctorName, long startMillis, long endMillis) {
        LiveData<List<Appointment>> liveData = repo.detectConflicts(doctorName, startMillis, endMillis);
        List<Appointment> conflicts = liveData.getValue();
        return conflicts != null && !conflicts.isEmpty();
    }

    public List<Appointment> getConflicts(String doctorName, long startMillis, long endMillis) {
        LiveData<List<Appointment>> liveData = repo.detectConflicts(doctorName, startMillis, endMillis);
        return liveData.getValue();
    }

    public LiveData<List<Appointment>> getConflictsLiveData(String doctorName, long startMillis, long endMillis) {
        return repo.detectConflicts(doctorName, startMillis, endMillis);
    }
}




