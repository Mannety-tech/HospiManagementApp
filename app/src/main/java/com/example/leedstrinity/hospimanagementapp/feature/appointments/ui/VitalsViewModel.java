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

    // Insert vitals for a patient
    public void insert(@NonNull Vitals vitals, long patientId) {
        vitals.setPatientId(patientId);
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.insert(vitals));
    }

    // Record new vitals for a patient
    public void recordVitalsForPatient(long patientId,
                                       int systolicBP,
                                       int diastolicBP,
                                       int heartRate,
                                       double temperature,
                                       int oxygenLevel) {
        Vitals vitals = Vitals.forPatient(
                patientId,
                systolicBP,
                diastolicBP,
                heartRate,
                temperature,
                oxygenLevel
        );
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.insert(vitals));
    }

    // Update existing vitals
    public void update(@NonNull Vitals vitals) {
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.update(vitals));
    }

    // Get all vitals for a patient
    public LiveData<List<Vitals>> getVitalsForPatient(long patientId) {
        return vitalsDao.getVitalsForPatient(patientId);
    }

    // Get vitals between two dates
    public LiveData<List<Vitals>> getVitalsBetweenDates(@NonNull Date start, @NonNull Date end) {
        return vitalsDao.findBetweenDates(start, end);
    }

    // Get latest vitals for a patient
    public LiveData<Vitals> getLatestVitalsForPatient(long patientId) {
        return vitalsDao.findLatestForPatient(patientId);
    }
}





