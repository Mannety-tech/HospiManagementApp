package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.VitalsDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.util.Date;
import java.util.List;

public class VitalsViewModel extends AndroidViewModel {

    private final VitalsDao vitalsDao;

    public VitalsViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        this.vitalsDao = db.vitalsDao();
    }

    /**
     * Insert a new vitals record for a patient.
     * Ensures patientId is always set before persisting.
     */
    public void insert(@NonNull Vitals vitals, @NonNull String patientId) {
        vitals.setPatientId(patientId);
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.insert(vitals));
    }

    /**
     * Convenience helper to record vitals directly for a patient.
     */
    public void recordVitalsForPatient(@NonNull String patientId,
                                       int heartRate,
                                       int systolicBP,
                                       int diastolicBP,
                                       double temperature,
                                       int respiratoryRate) {
        Vitals vitals = Vitals.forPatient(
                patientId,
                heartRate,
                systolicBP,
                diastolicBP,
                temperature,
                respiratoryRate
        );
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.insert(vitals));
    }

    /**
     * Update an existing vitals record.
     */
    public void update(@NonNull Vitals vitals) {
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.update(vitals));
    }

    /**
     * Observe all vitals for a given patient.
     */
    public LiveData<List<Vitals>> getVitalsForPatient(@NonNull String patientId) {
        return vitalsDao.findByPatient(patientId);
    }

    /**
     * Observe vitals recorded between two dates.
     */
    public LiveData<List<Vitals>> getVitalsBetweenDates(@NonNull Date start, @NonNull Date end) {
        return vitalsDao.findBetweenDates(start, end);
    }

    /**
     * Observe the latest vitals entry for a patient.
     */
    public LiveData<Vitals> getLatestVitalsForPatient(@NonNull String patientId) {
        return vitalsDao.findLatestForPatient(patientId);
    }
}



