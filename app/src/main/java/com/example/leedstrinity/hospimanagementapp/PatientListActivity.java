package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.PatientAdapter;

import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    private PatientAdapter patientAdapter;
    private PatientViewModel patientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPatients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        patientAdapter = new PatientAdapter(new ArrayList<>());
        recyclerView.setAdapter(patientAdapter);

        // --- Observe patients from ViewModel ---
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        patientViewModel.getAllPatients().observe(this, patients -> {
            patientAdapter.updatePatients(patients); // add updatePatients() method in adapter
        });
    }
}


