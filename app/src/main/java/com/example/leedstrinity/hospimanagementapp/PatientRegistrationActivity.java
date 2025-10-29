package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class PatientRegistrationActivity extends AppCompatActivity {

    private EditText etNhs, etFullName, etDob, etPhone, etEmail;
    private Button btnSavePatient;
    private Button backToMainButton; // Changed from View to Button for clarity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration); // Ensure this matches your XML filename

        // Toolbar setup
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Register Patient");
        }

        // Initialize views
        etNhs = findViewById(R.id.etNhs);
        etFullName = findViewById(R.id.etFullName);
        etDob = findViewById(R.id.etDob);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        btnSavePatient = findViewById(R.id.btnSavePatient);
        backToMainButton = findViewById(R.id.btnBackToMain); // Make sure this ID exists in your XML

        // Save button click listener
        btnSavePatient.setOnClickListener(v -> {
            String nhs = etNhs.getText().toString().trim();
            String name = etFullName.getText().toString().trim();
            String dob = etDob.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (nhs.isEmpty() || name.isEmpty() || dob.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Save patient data to database or send to server
                Toast.makeText(this, "Patient saved successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button click listener
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientRegistrationActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}


