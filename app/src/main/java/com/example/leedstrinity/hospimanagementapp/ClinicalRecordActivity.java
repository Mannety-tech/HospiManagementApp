package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ClinicalRecordActivity extends AppCompatActivity {

    // Form fields
    private EditText patientNameEditText;
    private EditText diagnosisEditText;
    private EditText treatmentEditText;
    private EditText notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_records);

        // Bind UI elements
        patientNameEditText = findViewById(R.id.editTextPatientName);
        diagnosisEditText = findViewById(R.id.editTextDiagnosis);
        treatmentEditText = findViewById(R.id.editTextTreatment);
        notesEditText = findViewById(R.id.editTextNotes);

        Button saveRecordButton = findViewById(R.id.buttonSaveRecord);
        Button viewVitalsButton = findViewById(R.id.buttonViewVitals);
        Button clinicianFormButton = findViewById(R.id.buttonClinicianForm);
        Button clinicianListButton = findViewById(R.id.buttonClinicianList);

        // Save record logic
        saveRecordButton.setOnClickListener(v -> {
            String name = patientNameEditText.getText().toString().trim();
            String diagnosis = diagnosisEditText.getText().toString().trim();
            String treatment = treatmentEditText.getText().toString().trim();
            String notes = notesEditText.getText().toString().trim();

            if (name.isEmpty() || diagnosis.isEmpty() || treatment.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Clinical record saved for " + name,
                        Toast.LENGTH_LONG).show();
                // TODO: Save to database or send to server
            }
        });

        // Navigate to Vitals page
        viewVitalsButton.setOnClickListener(v -> {
            String patientId = patientNameEditText.getText().toString().trim();
            // Replace with actual patientId from DB if available

            Intent intent = new Intent(this, VitalsActivity.class);
            intent.putExtra("patientId", patientId);
            startActivity(intent);
        });

        // Open Clinician Form
        clinicianFormButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClinicianFormActivity.class);
            startActivity(intent);
        });

        // Open Clinician List
        clinicianListButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ClinicianListActivity.class);
            startActivity(intent);
        });
    }
}








