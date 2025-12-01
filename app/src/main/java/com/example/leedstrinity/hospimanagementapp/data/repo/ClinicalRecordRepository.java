package com.example.leedstrinity.hospimanagementapp.data.repo;


import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicalRecordDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.ClinicalRecord;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClinicalRecordRepository {

    private final ClinicalRecordDao clinicalRecordDao;
    private final ExecutorService executorService;

    public ClinicalRecordRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        clinicalRecordDao = db.clinicalRecordDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertClinicalRecord(ClinicalRecord record) {
        executorService.execute(() -> clinicalRecordDao.insert(record));
    }

    public void updateClinicalRecord(ClinicalRecord record) {
        executorService.execute(() -> clinicalRecordDao.update(record));
    }

    public void deleteClinicalRecord(ClinicalRecord record) {
        executorService.execute(() -> clinicalRecordDao.delete(record));
    }

    public List<ClinicalRecord> getRecordsForPatient(String patientId) {
        return clinicalRecordDao.findByPatient(patientId);
    }

    public ClinicalRecord getLatestRecordForPatient(String patientId) {
        return clinicalRecordDao.findLatestForPatient(patientId);
    }
}

