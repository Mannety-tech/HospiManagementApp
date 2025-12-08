package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.util.Date;
import java.util.concurrent.Executors;

public class PatientDashboardActivity extends AppCompatActivity {

    private Button btnBookAppointment, btnViewAppointments, btnVitals, btnLogout;
    private TextView tvWelcomePatient, tvPatientDetails, tvAppointments, tvVitals;
    private long patientId;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);

        // Bind views
        tvWelcomePatient = findViewById(R.id.tvWelcomePatient);
        tvPatientDetails = findViewById(R.id.tvPatientDetails);
        tvAppointments = findViewById(R.id.tvAppointments);
        tvVitals = findViewById(R.id.tvVitals);

        btnBookAppointment = findViewById(R.id.btnBookAppointment);
        btnViewAppointments = findViewById(R.id.btnViewAppointments);
        btnVitals = findViewById(R.id.btnVitals);
        btnLogout = findViewById(R.id.btnLogout);

        // Get extras from LoginActivity
        Intent intent = getIntent();
        patientId = intent.getLongExtra("patientId", -1);
        String patientName = intent.getStringExtra("patientName");
        String patientEmail = intent.getStringExtra("patientEmail");
        String nhsNumber = intent.getStringExtra("nhsNumber");

        tvWelcomePatient.setText("Hello, " + patientName + " 🌿");
        tvPatientDetails.setText("Email: " + patientEmail + "\nNHS Number: " + nhsNumber);

        db = AppDatabase.getInstance(this);

        // --- Load latest appointment ---
        Executors.newSingleThreadExecutor().execute(() -> {
            Appointment latestAppt = db.appointmentDao().getLatestAppointmentForPatientSync(patientId);
            runOnUiThread(() -> {
                if (latestAppt != null) {
                    String formattedTime = DateFormat.format("HH:mm", new Date(latestAppt.getStartTimeMillis())).toString();
                    tvAppointments.setText("Last Appointment:\n" +
                            latestAppt.getDate() + " at " + formattedTime +
                            "\nClinic: " + latestAppt.getClinicLocation());
                } else {
                    tvAppointments.setText("No appointments booked yet.");
                }
            });
        });

        // --- Load latest vitals ---
        Executors.newSingleThreadExecutor().execute(() -> {
            Vitals latestVitals = db.vitalsDao().findLatestForPatientSync(patientId);
            runOnUiThread(() -> {
                if (latestVitals != null) {
                    tvVitals.setText("Latest Vitals:\n" +
                            "BP: " + latestVitals.getBloodPressure() +
                            "\nHR: " + latestVitals.getHeartRate() +
                            "\nTemp: " + latestVitals.getTemperature() +
                            "\nRecorded: " + latestVitals.getRecordedAt());
                } else {
                    tvVitals.setText("No vitals recorded yet.");
                }
            });
        });

        // --- Buttons ---
        btnBookAppointment.setOnClickListener(v -> {
            Intent apptIntent = new Intent(this, BookAppointmentActivity.class);
            apptIntent.putExtra("patientId", patientId);
            apptIntent.putExtra("patientName", patientName);
            apptIntent.putExtra("patientEmail", patientEmail);
            apptIntent.putExtra("nhsNumber", nhsNumber);
            startActivity(apptIntent);
        });

        btnViewAppointments.setOnClickListener(v -> {
            Intent viewIntent = new Intent(this, ViewAppointmentsActivity.class);
            viewIntent.putExtra("patientId", patientId);
            startActivity(viewIntent);
        });

        btnVitals.setOnClickListener(v -> {
            Intent vitalsIntent = new Intent(this, PatientVitalsActivity.class);
            vitalsIntent.putExtra("patientId", patientId);
            vitalsIntent.putExtra("patientName", patientName);
            vitalsIntent.putExtra("patientEmail", patientEmail);
            vitalsIntent.putExtra("nhsNumber", nhsNumber);
            startActivity(vitalsIntent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent logoutIntent = new Intent(PatientDashboardActivity.this, LandingActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(logoutIntent);
            finish();
        });
    }

    // --- Refresh data when returning from other activities ---
    @Override
    protected void onResume() {
        super.onResume();

        // Refresh latest appointment
        Executors.newSingleThreadExecutor().execute(() -> {
            Appointment latestAppt = db.appointmentDao().getLatestAppointmentForPatientSync(patientId);
            runOnUiThread(() -> {
                if (latestAppt != null) {
                    String formattedTime = DateFormat.format("HH:mm", new Date(latestAppt.getStartTimeMillis())).toString();
                    tvAppointments.setText("Last Appointment:\n" +
                            latestAppt.getDate() + " at " + formattedTime +
                            "\nClinic: " + latestAppt.getClinicLocation());
                } else {
                    tvAppointments.setText("No appointments booked yet.");
                }
            });
        });

        // Refresh latest vitals
        Executors.newSingleThreadExecutor().execute(() -> {
            Vitals latestVitals = db.vitalsDao().findLatestForPatientSync(patientId);
            runOnUiThread(() -> {
                if (latestVitals != null) {
                    tvVitals.setText("Latest Vitals:\n" +
                            "BP: " + latestVitals.getBloodPressure() +
                            "\nHR: " + latestVitals.getHeartRate() +
                            "\nTemp: " + latestVitals.getTemperature() +
                            "\nRecorded: " + latestVitals.getRecordedAt());
                } else {
                    tvVitals.setText("No vitals recorded yet.");
                }
            });
        });
    }
}












