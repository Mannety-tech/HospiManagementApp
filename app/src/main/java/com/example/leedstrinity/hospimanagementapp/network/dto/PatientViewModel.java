package com.example.leedstrinity.hospimanagementapp.network.dto;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.PatientDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PatientViewModel extends AndroidViewModel {

    private final PatientDao patientDao;
    private final ExecutorService executorService;

    public PatientViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        patientDao = db.patientDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    // --- Insert patient safely off the main thread ---
    public void insert(Patient patient) {
        executorService.execute(() -> patientDao.insert(patient));
    }

    public void update(Patient patient) {
        executorService.execute(() -> patientDao.update(patient));
    }

    public LiveData<List<Patient>> getAllPatients() {
        return patientDao.getAllPatients();
    }

    // Use the correct DAO method and long type
    public LiveData<Patient> findPatientById(long id) {
        return patientDao.findByIdLive(id);
    }

    // Optional: synchronous login (not LiveData)
    public Patient login(String email, String password) {
        return patientDao.login(email, password);
    }
}




