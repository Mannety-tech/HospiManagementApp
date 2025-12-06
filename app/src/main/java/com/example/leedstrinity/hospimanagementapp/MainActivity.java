package com.example.leedstrinity.hospimanagementapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.security.auth.SecureDatabaseHelper;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // --- Notification channel for admin alerts ---
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

        // --- Redirect staff from signup directly to AdminPortalActivity ---
        Intent intent = getIntent();
        if (intent.hasExtra("staffEmail")) {
            String staffName = intent.getStringExtra("staffName");
            String staffEmail = intent.getStringExtra("staffEmail");
            String empNo = intent.getStringExtra("employeeNumber");
            String specialty = intent.getStringExtra("specialty");

            Toast.makeText(this, "Welcome " + staffName + " (" + specialty + ")", Toast.LENGTH_LONG).show();

            Intent adminIntent = new Intent(MainActivity.this, AdminPortalActivity.class);
            adminIntent.putExtra("staffName", staffName);
            adminIntent.putExtra("staffEmail", staffEmail);
            adminIntent.putExtra("employeeNumber", empNo);
            adminIntent.putExtra("specialty", specialty);
            startActivity(adminIntent);
            finish();
            return;
        }

        // --- Initialize Room database ---
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "hospital_db"
        ).fallbackToDestructiveMigration().build();

        // --- Insert a test patient for demo purposes ---
        Executors.newSingleThreadExecutor().execute(() -> {
            Patient testPatient = new Patient(
                    "John Doe",
                    "Male",
                    "1990-01-01",
                    "07123456789",
                    "123 Test Street",
                    "NHS123456",
                    "john@example.com",
                    "password123",
                    "patient"
            );
            db.patientDao().insert(testPatient);
            Log.d("MainActivity", "Inserted test patient: " + testPatient.getName());
        });

        // --- Handle system bar insets ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Test SecureDatabaseHelper ---
        String testReason = "Follow-up consultation";
        String encryptedReason = SecureDatabaseHelper.encrypt(testReason);
        Log.d("SecureDBTest", "Encrypted: " + encryptedReason);
        String decryptedReason = SecureDatabaseHelper.decrypt(encryptedReason);
        Log.d("SecureDBTest", "Decrypted: " + decryptedReason);

        // --- Buttons ---
        findViewById(R.id.btn_register).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, PatientRegistrationActivity.class)));

        findViewById(R.id.btn_admin_portal).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AdminPortalActivity.class)));

        findViewById(R.id.btn_admin_login).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AdminLoginActivity.class)));

        findViewById(R.id.btn_appointment).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, BookAppointmentActivity.class)));

        findViewById(R.id.btn_patient_record).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, PatientRecordActivity.class)));

        findViewById(R.id.btn_clear_session).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ClearSessionActivity.class)));

        findViewById(R.id.btn_biometric_auth).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, BiometricAuthActivity.class)));

        findViewById(R.id.btn_clinical_record).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ClinicalRecordActivity.class)));

        findViewById(R.id.btn_patient_dashboard).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, PatientDashboardActivity.class)));

        // --- Vitals Page button (fetch latest patient dynamically) ---
        findViewById(R.id.btn_vitals_page).setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                List<Patient> patients = db.patientDao().getAllPatientsSync();
                if (patients != null && !patients.isEmpty()) {
                    Patient latestPatient = patients.get(patients.size() - 1);
                    runOnUiThread(() -> {
                        Intent vitalsIntent = new Intent(MainActivity.this, PatientVitalsActivity.class);
                        vitalsIntent.putExtra("patientId", latestPatient.getId());
                        startActivity(vitalsIntent);
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "No patients found", Toast.LENGTH_SHORT).show());
                }
            });
        });
    }
}










