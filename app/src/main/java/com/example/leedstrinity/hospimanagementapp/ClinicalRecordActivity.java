package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.network.dto.PatientViewModel;

public class ClinicalRecordActivity extends AppCompatActivity {

    private EditText patientNameEditText;
    private EditText diagnosisEditText;
    private EditText treatmentEditText;
    private EditText notesEditText;

    private PatientViewModel patientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_records);

        // Bind UI
        patientNameEditText = findViewById(R.id.editTextPatientName);
        diagnosisEditText = findViewById(R.id.editTextDiagnosis);
        treatmentEditText = findViewById(R.id.editTextTreatment);
        notesEditText = findViewById(R.id.editTextNotes);

        Button saveRecordButton = findViewById(R.id.buttonSaveRecord);
        Button viewVitalsButton = findViewById(R.id.buttonViewVitals);
        Button clinicianFormButton = findViewById(R.id.buttonClinicianForm);
        Button clinicianListButton = findViewById(R.id.buttonClinicianList);

        // Init ViewModel
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        // Save record logic
        saveRecordButton.setOnClickListener(v -> {
            String name = patientNameEditText.getText().toString().trim();
            String diagnosis = diagnosisEditText.getText().toString().trim();
            String treatment = treatmentEditText.getText().toString().trim();
            String notes = notesEditText.getText().toString().trim();

            if (name.isEmpty() || diagnosis.isEmpty() || treatment.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
            } else {
                // Create and insert patient (demo placeholders for missing fields)
                Patient patient = new Patient(
                        name, "", "", "", "", "NHS123456", "email@example.com", "password", "patient"
                );
                patientViewModel.insert(patient);

                Toast.makeText(this,
                        "Clinical record saved for " + name,
                        Toast.LENGTH_LONG).show();
            }
        });

        // Navigate to Vitals page
        viewVitalsButton.setOnClickListener(v -> {
            patientViewModel.getAllPatients().observe(this, patients -> {
                if (patients != null && !patients.isEmpty()) {
                    Patient patient = patients.get(0); // demo: first patient
                    Intent intent = new Intent(this, VitalsActivity.class);
                    intent.putExtra("patientId", patient.getId());
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No patients found", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Open Clinician Form
        clinicianFormButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ClinicianFormActivity.class));
        });

        // Open Clinician List
        clinicianListButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ClinicianListActivity.class));
        });
    }
}










