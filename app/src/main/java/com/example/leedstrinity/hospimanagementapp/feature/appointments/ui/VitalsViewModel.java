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
        vitalsDao = db.vitalsDao();
    }

    /**
     * Insert new vitals record, ensuring patientId is set.
     */
    public void insert(Vitals vitals, @NonNull String patientId) {
        vitals.setPatientId(patientId);   // critical: must not be null
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.insert(vitals));
    }

    /**
     * Convenience helper: record vitals directly for a patient.
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
     * Update existing vitals record.
     */
    public void update(Vitals vitals) {
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.update(vitals));
    }

    /**
     * Observe all vitals for a patient.
     */
    public LiveData<List<Vitals>> getVitalsForPatient(String patientId) {
        return vitalsDao.findByPatient(patientId);
    }

    /**
     * Observe vitals between two dates.
     */
    public LiveData<List<Vitals>> getVitalsBetweenDates(Date start, Date end) {
        return vitalsDao.findBetweenDates(start, end);
    }

    /**
     * Observe the latest vitals for a patient.
     */
    public LiveData<Vitals> getLatestVitalsForPatient(String patientId) {
        return vitalsDao.findLatestForPatient(patientId);
    }
}


