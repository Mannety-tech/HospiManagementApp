package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button openRegistrationButton = findViewById(R.id.btn_register);
        openRegistrationButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PatientRegistrationActivity.class);
            startActivity(intent);
        });

        Button openAdminPortalButton = findViewById(R.id.btn_admin_portal);
        openAdminPortalButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminPortalActivity.class);
            startActivity(intent);
        });

        Button openAdminLoginButton = findViewById(R.id.btn_admin_login);
        openAdminLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
            startActivity(intent);
        });

        Button openAppointmentButton = findViewById(R.id.btn_appointment);
        openAppointmentButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AppointmentActivity.class);
            startActivity(intent);
        });

        Button openPatientRecordButton = findViewById(R.id.btn_patient_record);
        openPatientRecordButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PatientRecordActivity.class);
            startActivity(intent);
        });

        Button openClearSessionButton = findViewById(R.id.btn_clear_session);
        openClearSessionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ClearSessionActivity.class);
            startActivity(intent);
        });

        Button openBiometricAuthButton = findViewById(R.id.btn_biometric_auth);
        openBiometricAuthButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BiometricAuthActivity.class);
            startActivity(intent);
        });



    }
}



