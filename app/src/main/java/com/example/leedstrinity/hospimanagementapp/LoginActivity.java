package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.data.entities.Staff;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button patientLoginBtn, staffLoginBtn, btnGoToSignup, btnBack;
    private TextView forgotPassword;

    private AppDatabase db; // Room database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // --- Bind views ---
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        patientLoginBtn = findViewById(R.id.btn_patient_login);
        staffLoginBtn = findViewById(R.id.btn_staff_login);
        btnGoToSignup = findViewById(R.id.btnGoToSignup);
        forgotPassword = findViewById(R.id.tvForgotPassword);

        // --- Use singleton AppDatabase ---
        db = AppDatabase.getInstance(this);

        // --- Patient login ---
        patientLoginBtn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Patient patient = db.patientDao().login(email, password);
                runOnUiThread(() -> {
                    if (patient != null) {
                        Intent intent = new Intent(LoginActivity.this, PatientDashboardActivity.class);
                        intent.putExtra("patientName", patient.getName());
                        intent.putExtra("patientEmail", patient.getEmail());
                        intent.putExtra("nhsNumber", patient.getNhsNumber());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid patient credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        // --- Staff login ---
        staffLoginBtn.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Staff staff = db.staffDao().login(email, password);
                runOnUiThread(() -> {
                    if (staff != null) {
                        Intent intent = new Intent(LoginActivity.this, AdminPortalActivity.class);
                        intent.putExtra("staffName", staff.getName());
                        intent.putExtra("staffEmail", staff.getEmail());
                        intent.putExtra("employeeNumber", staff.getEmployeeNumber());
                        intent.putExtra("specialty", staff.getSpecialty());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid staff credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });


        // --- Go to Signup ---
        btnGoToSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // --- Forgot password ---
        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }
}

