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
    private int patientId; // ✅ use int not String

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

        // --- Get patientId from Intent ---
        patientId = getIntent().getIntExtra("patientId", -1);
        if (patientId == -1) {
            Toast.makeText(this, "No patient ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // --- Setup RecyclerView ---
        RecyclerView recyclerView = findViewById(R.id.vitalsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vitalsAdapter = new VitalsAdapter(new ArrayList<>());
        recyclerView.setAdapter(vitalsAdapter);

        // --- Init ViewModel ---
        vitalsViewModel = new ViewModelProvider(this).get(VitalsViewModel.class);

        // --- Observe vitals for this patient ---
        vitalsViewModel.getVitalsForPatient(patientId).observe(this, vitalsList -> {
            if (vitalsList != null && !vitalsList.isEmpty()) {
                vitalsAdapter.updateVitals(vitalsList);
            } else {
                Toast.makeText(this, "No vitals recorded for patient ID " + patientId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}









