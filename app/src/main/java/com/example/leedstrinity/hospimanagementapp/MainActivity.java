package com.example.leedstrinity.hospimanagementapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.leedstrinity.hospimanagementapp.security.auth.SecureDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // --- Create notification channel for admin alerts ---
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "admin_channel",
                    "Admin Alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for admin actions");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //  Check if staff came from signup
        Intent intent = getIntent();
        if (intent.hasExtra("staffEmail")) {
            String staffName = intent.getStringExtra("staffName");
            String staffEmail = intent.getStringExtra("staffEmail");
            String empNo = intent.getStringExtra("employeeNumber");
            String specialty = intent.getStringExtra("specialty");

            Toast.makeText(this, "Welcome " + staffName + " (" + specialty + ")", Toast.LENGTH_LONG).show();

            //  Redirect directly to AdminPortalActivity
            Intent adminIntent = new Intent(MainActivity.this, AdminPortalActivity.class);
            adminIntent.putExtra("staffName", staffName);
            adminIntent.putExtra("staffEmail", staffEmail);
            adminIntent.putExtra("employeeNumber", empNo);
            adminIntent.putExtra("specialty", specialty);
            startActivity(adminIntent);
            finish();
            return;
        }

        // Initialize Room database
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "hospital_db"
                ).fallbackToDestructiveMigration()
                .build();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Test SecureDatabaseHelper
        String testReason = "Follow-up consultation";
        String encryptedReason = SecureDatabaseHelper.encrypt(testReason);
        Log.d("SecureDBTest", "Encrypted: " + encryptedReason);
        String decryptedReason = SecureDatabaseHelper.decrypt(encryptedReason);
        Log.d("SecureDBTest", "Decrypted: " + decryptedReason);

        // Buttons (normal flow if not staff)
        Button openRegistrationButton = findViewById(R.id.btn_register);
        openRegistrationButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PatientRegistrationActivity.class));
        });

        Button openAdminPortalButton = findViewById(R.id.btn_admin_portal);
        openAdminPortalButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AdminPortalActivity.class));
        });

        Button openAdminLoginButton = findViewById(R.id.btn_admin_login);
        openAdminLoginButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
        });

        // ✅ FIXED: declare the button before using it
        Button openAppointmentButton = findViewById(R.id.btn_appointment);
        openAppointmentButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BookAppointmentActivity.class));
        });

        Button openPatientRecordButton = findViewById(R.id.btn_patient_record);
        openPatientRecordButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PatientRecordActivity.class));
        });

        Button openClearSessionButton = findViewById(R.id.btn_clear_session);
        openClearSessionButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ClearSessionActivity.class));
        });

        Button openBiometricAuthButton = findViewById(R.id.btn_biometric_auth);
        openBiometricAuthButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, BiometricAuthActivity.class));
        });

        Button openClinicalRecordButton = findViewById(R.id.btn_clinical_record);
        openClinicalRecordButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ClinicalRecordActivity.class));
        });

        Button patientDashboardBtn = findViewById(R.id.btn_patient_dashboard);
        patientDashboardBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PatientDashboardActivity.class));
        });
    }
}








