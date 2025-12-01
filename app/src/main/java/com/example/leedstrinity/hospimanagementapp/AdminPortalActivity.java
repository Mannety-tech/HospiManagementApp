package com.example.leedstrinity.hospimanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

    // Greeting views
    private TextView tvWelcomeAdmin, tvAdminDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);

        // --- Greeting section ---
        tvWelcomeAdmin = findViewById(R.id.tvWelcomeAdmin);
        tvAdminDetails = findViewById(R.id.tvAdminDetails);

        // ✅ Get staff extras passed from MainActivity
        Intent intent = getIntent();
        String staffName = intent.getStringExtra("staffName");
        String staffEmail = intent.getStringExtra("staffEmail");
        String empNo = intent.getStringExtra("employeeNumber");
        String specialty = intent.getStringExtra("specialty");

        if (staffName != null) {
            tvWelcomeAdmin.setText("Welcome, " + staffName + " 👋");
            tvAdminDetails.setText("Email: " + staffEmail +
                    "\nEmployee No: " + empNo +
                    "\nSpecialty: " + specialty);
            Toast.makeText(this, "Admin portal loaded for " + staffName, Toast.LENGTH_SHORT).show();
        } else {
            tvWelcomeAdmin.setText("Welcome to Admin Portal");
            tvAdminDetails.setText("No staff details found.");
        }

        // --- Staff Management ---
        staffNameEditText = findViewById(R.id.etStaffName);
        staffEmailEditText = findViewById(R.id.etStaffEmail);
        roleSpinner = findViewById(R.id.spRole);
        registerStaffButton = findViewById(R.id.btnRegisterStaff);
        staffRecyclerView = findViewById(R.id.rvStaff);

        ArrayAdapter<CharSequence> roleAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.staff_roles,
                android.R.layout.simple_spinner_item
        );
        roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(roleAdapter);

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

        ArrayAdapter<String> patientAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Patient A", "Patient B", "Patient C"}
        );
        patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(patientAdapter);

        registerPatientButton.setOnClickListener(v -> {
            startActivity(new Intent(this, PatientRegistrationActivity.class));
        });

        bookAppointmentButton.setOnClickListener(v -> {
            String selectedPatient = patientSpinner.getSelectedItem().toString();
            Toast.makeText(this, "Book appointment for " + selectedPatient, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BookAppointmentActivity.class));
        });

        viewAppointmentsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AppointmentListActivity.class));
        });

        logoutButton.setOnClickListener(v -> {
            Intent backToMain = new Intent(AdminPortalActivity.this, MainActivity.class);
            backToMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(backToMain);
            finish();
        });
    }
}



