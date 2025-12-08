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
    private long patientId = -1; // current patient ID
    private String staffName, staffEmail, employeeNumber, specialty;

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

        // --- Get extras from LoginActivity ---
        Intent intent = getIntent();
        if (intent.hasExtra("staffEmail")) {
            staffName = intent.getStringExtra("staffName");
            staffEmail = intent.getStringExtra("staffEmail");
            employeeNumber = intent.getStringExtra("employeeNumber");
            specialty = intent.getStringExtra("specialty");

            Toast.makeText(this, "Welcome " + staffName + " (" + specialty + ")", Toast.LENGTH_LONG).show();
        } else if (intent.hasExtra("patientId")) {
            patientId = intent.getLongExtra("patientId", -1);
            String patientName = intent.getStringExtra("patientName");
            Toast.makeText(this, "Welcome " + patientName, Toast.LENGTH_LONG).show();
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

        // --- Record New Vitals button ---
        findViewById(R.id.btn_vitals_page).setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                List<Patient> patients = db.patientDao().getAllPatientsSync();
                if (patients != null && !patients.isEmpty()) {
                    Patient latestPatient = patients.get(patients.size() - 1);
                    patientId = latestPatient.getId();
                    runOnUiThread(() -> {
                        Intent vitalsIntent = new Intent(MainActivity.this, RecordVitalActivity.class);
                        vitalsIntent.putExtra("patientId", patientId);
                        startActivity(vitalsIntent);
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "No patients found", Toast.LENGTH_SHORT).show());
                }
            });
        });

        // --- View Details button ---
        findViewById(R.id.btn_view_details).setOnClickListener(v -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                List<Patient> patients = db.patientDao().getAllPatientsSync();
                if (patients != null && !patients.isEmpty()) {
                    Patient latestPatient = patients.get(patients.size() - 1);
                    runOnUiThread(() -> {
                        Intent detailsIntent = new Intent(MainActivity.this, PatientDetailsActivity.class);
                        detailsIntent.putExtra("patientId", latestPatient.getId());
                        startActivity(detailsIntent);
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(MainActivity.this, "No patients found", Toast.LENGTH_SHORT).show());
                }
            });
        });
    }
}














