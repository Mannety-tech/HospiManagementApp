package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.VitalsViewModel;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.VitalsAdapter;

public class PatientVitalsActivity extends AppCompatActivity {

    private VitalsAdapter vitalsAdapter;
    private VitalsViewModel vitalsViewModel;
    private long patientId;

    private TextView tvPatientName;
    private Button btnAddVitals;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_vitals);

        // --- Get patientId from Intent ---
        patientId = getIntent().getLongExtra("patientId", -1);
        if (patientId == -1) {
            Toast.makeText(this, "No patient ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // --- Setup header ---
        tvPatientName = findViewById(R.id.tvPatientName);
        tvPatientName.setText("Patient ID: " + patientId);

        // --- Setup RecyclerView ---
        RecyclerView recyclerView = findViewById(R.id.recyclerVitals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vitalsAdapter = new VitalsAdapter();
        recyclerView.setAdapter(vitalsAdapter);

        // --- Init ViewModel ---
        vitalsViewModel = new ViewModelProvider(this).get(VitalsViewModel.class);

        // --- Observe vitals for this patient ---
        vitalsViewModel.getVitalsForPatient(patientId).observe(this, vitalsList -> {
            if (vitalsList != null && !vitalsList.isEmpty()) {
                vitalsAdapter.setVitals(vitalsList);
            } else {
                Toast.makeText(this, "No vitals recorded for patient ID " + patientId, Toast.LENGTH_SHORT).show();
            }
        });

        // --- Record New Vitals button ---
        btnAddVitals = findViewById(R.id.btnAddVitals);
        btnAddVitals.setOnClickListener(v -> {
            Intent intent = new Intent(PatientVitalsActivity.this, RecordVitalActivity.class);
            intent.putExtra("patientId", patientId);
            startActivity(intent);
        });
    }
}


