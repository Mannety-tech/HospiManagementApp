package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;

public class PatientLoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoToSignup;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btn_patient_login);
        btnGoToSignup = findViewById(R.id.btnGoToSignup);

        //  Use the singleton instance with consistent DB name
        db = AppDatabase.getInstance(this);

        // Patient login
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                Patient patient = db.patientDao().login(email, password);

                runOnUiThread(() -> {
                    if (patient != null) {
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // ✅ Use getters instead of direct field access
                        Intent intent = new Intent(PatientLoginActivity.this, PatientDashboardActivity.class);
                        intent.putExtra("patientName", patient.getName());
                        intent.putExtra("patientEmail", patient.getEmail());
                        intent.putExtra("nhsNumber", patient.getNhsNumber());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        });

        // Go to signup
        btnGoToSignup.setOnClickListener(v -> {
            startActivity(new Intent(PatientLoginActivity.this, SignupActivity.class));
        });
    }
}




