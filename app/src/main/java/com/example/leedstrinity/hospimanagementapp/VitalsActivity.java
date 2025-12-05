package com.example.leedstrinity.hospimanagementapp;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.VitalsViewModel;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.VitalsAdapter;

import java.util.ArrayList;

public class VitalsActivity extends AppCompatActivity {

    private VitalsViewModel vitalsViewModel;
    private VitalsAdapter vitalsAdapter;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

        // --- Get patientId from Intent ---
        patientId = getIntent().getStringExtra("patientId");
        if (patientId == null || patientId.isEmpty()) {
            Toast.makeText(this, "No patient ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // --- Setup RecyclerView ---
        RecyclerView recyclerView = findViewById(R.id.vitalsRecyclerView); // ensure XML id matches
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vitalsAdapter = new VitalsAdapter(new ArrayList<>()); // start with empty list
        recyclerView.setAdapter(vitalsAdapter);

        // --- Init ViewModel ---
        vitalsViewModel = new ViewModelProvider(this).get(VitalsViewModel.class);

        // --- Observe vitals for this patient ---
        vitalsViewModel.getVitalsForPatient(patientId).observe(this, vitalsList -> {
            if (vitalsList != null && !vitalsList.isEmpty()) {
                vitalsAdapter.updateVitals(vitalsList); // refresh adapter data
            } else {
                Toast.makeText(this, "No vitals recorded for " + patientId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}








