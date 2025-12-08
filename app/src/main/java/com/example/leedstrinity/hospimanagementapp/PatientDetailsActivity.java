package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;

import java.util.concurrent.Executors;

public class PatientDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private long patientId;

    private EditText etName, etGender, etDob, etPhone, etAddress, etNhs, etEmail;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);

        // Bind views
        etName = findViewById(R.id.etName);
        etGender = findViewById(R.id.etGender);
        etDob = findViewById(R.id.etDob);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etNhs = findViewById(R.id.etNhs);
        etEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);

        // Get patientId from intent
        patientId = getIntent().getLongExtra("patientId", -1);
        if (patientId == -1) {
            Toast.makeText(this, "No patient ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = AppDatabase.getInstance(this);

        // Load patient details
        Executors.newSingleThreadExecutor().execute(() -> {
            Patient patient = db.patientDao().findByIdSync(patientId);
            runOnUiThread(() -> {
                if (patient != null) {
                    etName.setText(patient.getName());
                    etGender.setText(patient.getGender());
                    etAddress.setText(patient.getAddress());
                    etNhs.setText(patient.getNhsNumber());
                    etEmail.setText(patient.getEmail());
                } else {
                    Toast.makeText(this, "Patient not found", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Save button updates patient record
        btnSave.setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                Patient updatedPatient = new Patient(
                        etName.getText().toString().trim(),
                        etGender.getText().toString().trim(),
                        etDob.getText().toString().trim(),
                        etPhone.getText().toString().trim(),
                        etAddress.getText().toString().trim(),
                        etNhs.getText().toString().trim(),
                        etEmail.getText().toString().trim(),
                        "password123", // keep existing password or handle separately
                        "patient"
                );
                updatedPatient.setId(patientId);

                db.patientDao().update(updatedPatient);

                runOnUiThread(() ->
                        Toast.makeText(PatientDetailsActivity.this, "Patient updated successfully", Toast.LENGTH_SHORT).show()
                );
            });
        });
    }
}

