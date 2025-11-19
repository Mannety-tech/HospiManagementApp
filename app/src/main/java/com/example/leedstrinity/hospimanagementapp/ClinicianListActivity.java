package com.example.leedstrinity.hospimanagementapp;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.data.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.ClinicianDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Clinician;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.ClinicianAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClinicianListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClinicianAdapter adapter;
    private ClinicianDao clinicianDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician_list);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerClinicians);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClinicianAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // Get DAO
        clinicianDao = AppDatabase.getInstance(this).clinicianDao();

        // Load clinicians (ideally off the main thread)
        new Thread(() -> {
            List<Clinician> clinicians = clinicianDao.getAllClinicians();
            runOnUiThread(() -> adapter.setClinicians(clinicians));
        }).start();
    }
}

