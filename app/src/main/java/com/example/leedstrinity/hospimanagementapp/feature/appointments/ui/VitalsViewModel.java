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


    public void insert(@NonNull Vitals vitals, int patientId) {
        vitals.setPatientId(patientId);
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.insert(vitals));
    }


    public void recordVitalsForPatient(int patientId,
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


    public void update(@NonNull Vitals vitals) {
        AppDatabase.databaseWriteExecutor.execute(() -> vitalsDao.update(vitals));
    }


    public LiveData<List<Vitals>> getVitalsForPatient(int patientId) {
        return vitalsDao.getVitalsForPatient(patientId);
    }


    public LiveData<List<Vitals>> getVitalsBetweenDates(@NonNull Date start, @NonNull Date end) {
        return vitalsDao.findBetweenDates(start, end);
    }


    public LiveData<Vitals> getLatestVitalsForPatient(int patientId) {
        return vitalsDao.findLatestForPatient(patientId);
    }
}




