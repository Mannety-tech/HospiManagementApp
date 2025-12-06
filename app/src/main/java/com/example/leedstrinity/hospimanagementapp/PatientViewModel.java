package com.example.leedstrinity.hospimanagementapp;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.PatientDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;

import java.util.List;

public class PatientViewModel extends AndroidViewModel {

    private final PatientDao patientDao;
    private final LiveData<List<Patient>> allPatients;

    public PatientViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        patientDao = db.patientDao();
        allPatients = patientDao.getAllPatients(); // DAO query returning LiveData
    }

    public LiveData<List<Patient>> getAllPatients() {
        return allPatients;
    }

    public void insert(Patient patient) {
        AppDatabase.databaseWriteExecutor.execute(() -> patientDao.insert(patient));
    }
}

