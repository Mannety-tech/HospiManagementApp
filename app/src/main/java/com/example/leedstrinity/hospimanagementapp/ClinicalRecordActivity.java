package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.AppointmentActivity;

public class ClinicalRecordActivity extends AppCompatActivity {

    private EditText patientNameEditText, diagnosisEditText, treatmentEditText, notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinical_records);

        patientNameEditText = findViewById(R.id.editTextPatientName);
        diagnosisEditText = findViewById(R.id.editTextDiagnosis);
        treatmentEditText = findViewById(R.id.editTextTreatment);
        notesEditText = findViewById(R.id.editTextNotes);

        Button saveRecordButton = findViewById(R.id.buttonSaveRecord);
        Button backButton = findViewById(R.id.buttonBackToAppointments);
        Button clinicianFormButton = findViewById(R.id.buttonClinicianForm);
        Button clinicianListButton = findViewById(R.id.buttonClinicianList);

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

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AppointmentActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        clinicianFormButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ClinicianFormActivity.class));
        });

        clinicianListButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ClinicianListActivity.class));
        });
    }
}



