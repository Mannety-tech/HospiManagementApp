package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PatientDashboardActivity extends AppCompatActivity {

    private Button btnBookAppointment, btnViewAppointments;
    private TextView tvWelcomePatient, tvPatientDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        // --- Greeting views ---
        tvWelcomePatient = findViewById(R.id.tvWelcomePatient);
        tvPatientDetails = findViewById(R.id.tvPatientDetails);

        // ✅ Get extras passed from LoginActivity
        Intent intent = getIntent();
        String patientName = intent.getStringExtra("patientName");
        String patientEmail = intent.getStringExtra("patientEmail");
        String nhsNumber = intent.getStringExtra("nhsNumber");

        if (patientName != null) {
            String safeEmail = (patientEmail != null) ? patientEmail : "N/A";
            String safeNhs = (nhsNumber != null) ? nhsNumber : "N/A";

            tvWelcomePatient.setText("Hello, " + patientName + " 🌿");
            tvPatientDetails.setText("Email: " + safeEmail +
                    "\nNHS Number: " + safeNhs);

            Toast.makeText(this,
                    "Patient dashboard ready for " + patientName,
                    Toast.LENGTH_SHORT).show();
        } else {
            tvWelcomePatient.setText("Welcome to Patient Dashboard");
            tvPatientDetails.setText("No patient details found.");
        }

        // --- Appointment buttons ---
        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        btnViewAppointments = findViewById(R.id.btnViewAppointments);

        btnBookAppointment.setOnClickListener(v -> {
            startActivity(new Intent(PatientDashboardActivity.this, BookAppointmentActivity.class));
        });

        btnViewAppointments.setOnClickListener(v -> {
            startActivity(new Intent(PatientDashboardActivity.this, ViewAppointmentsActivity.class));
        });
    }
}




