package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AdminPortalActivity extends AppCompatActivity {

    // Staff management views
    private EditText staffNameEditText, staffEmailEditText;
    private Spinner roleSpinner;
    private Button registerStaffButton;
    private RecyclerView staffRecyclerView;

    // Patient & appointment management views
    private Spinner patientSpinner;
    private Button registerPatientButton, bookAppointmentButton, viewAppointmentsButton, logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_login); // matches redesigned XML

        // --- Staff Management ---
        staffNameEditText = findViewById(R.id.etStaffName);
        staffEmailEditText = findViewById(R.id.etStaffEmail);
        roleSpinner = findViewById(R.id.spRole);
        registerStaffButton = findViewById(R.id.btnRegisterStaff);
        staffRecyclerView = findViewById(R.id.rvStaff);

        // Setup spinner with staff roles
        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.staff_roles,
                android.R.layout.simple_spinner_item
        );
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

        // Register staff logic (no PIN required anymore)
        registerStaffButton.setOnClickListener(v -> {
            String name = staffNameEditText.getText().toString().trim();
            String email = staffEmailEditText.getText().toString().trim();
            String role = roleSpinner.getSelectedItem().toString();

            if (name.isEmpty() || email.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Save staff record to database
            Toast.makeText(this, "Staff registered: " + name + " (" + role + ")", Toast.LENGTH_LONG).show();
        });

        // --- Patient & Appointment Management ---
        patientSpinner = findViewById(R.id.spPatientSelector);
        registerPatientButton = findViewById(R.id.btnRegisterPatient);
        bookAppointmentButton = findViewById(R.id.btnBookAppointment);
        viewAppointmentsButton = findViewById(R.id.btnViewAppointments);
        logoutButton = findViewById(R.id.btnLogout);

        // Example patient list (replace with DB query later)
        ArrayAdapter<String> patientAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Patient A", "Patient B", "Patient C"}
        );
        patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(patientAdapter);

        // Register patient
        registerPatientButton.setOnClickListener(v -> {
            Toast.makeText(this, "Navigate to Register Patient screen", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, PatientRegistrationActivity.class));
        });

        // Book appointment
        bookAppointmentButton.setOnClickListener(v -> {
            String selectedPatient = patientSpinner.getSelectedItem().toString();
            Toast.makeText(this, "Book appointment for " + selectedPatient, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BookAppointmentActivity.class));
        });

        // View appointments
        viewAppointmentsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AppointmentListActivity.class));
        });

        // Logout
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminPortalActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}


