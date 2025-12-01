
package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.entities.Clinician;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.ClinicianAdapter;

import java.util.concurrent.Executors;

public class ClinicianFormActivity extends AppCompatActivity {

    private EditText nameEditText, employeeNumberEditText, phoneEditText, emailEditText;
    private Spinner specialtySpinner;
    private Button saveButton;
    private RecyclerView rvClinicians;
    private ClinicianAdapter clinicianAdapter;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinician_form);

        // Room DB
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "hospital_db").build();

        // Form fields
        nameEditText = findViewById(R.id.etClinicianName);
        employeeNumberEditText = findViewById(R.id.etEmployeeNumber);
        phoneEditText = findViewById(R.id.etClinicianPhone);
        emailEditText = findViewById(R.id.etClinicianEmail);
        specialtySpinner = findViewById(R.id.spSpecialty);
        saveButton = findViewById(R.id.btnSaveClinician);

        // RecyclerView setup
        rvClinicians = findViewById(R.id.rvClinicians);
        rvClinicians.setLayoutManager(new LinearLayoutManager(this));
        clinicianAdapter = new ClinicianAdapter();
        rvClinicians.setAdapter(clinicianAdapter);

        // Populate specialties
        ArrayAdapter<String> specialtyAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"General Practice", "Cardiology", "Orthopedics", "Emergency"}
        );
        specialtySpinner.setAdapter(specialtyAdapter);

        // Save button logic
        saveButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String empNo = employeeNumberEditText.getText().toString().trim();
            String phone = phoneEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String specialty = specialtySpinner.getSelectedItem().toString();

            if (name.isEmpty() || empNo.isEmpty()) {
                Toast.makeText(this, "Name and Employee Number are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Clinician clinician = new Clinician(empNo, name, specialty, phone, email);

            Executors.newSingleThreadExecutor().execute(() -> {
                db.clinicianDao().insertClinician(clinician);

                // Refresh list from DB
                runOnUiThread(() -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        clinicianAdapter.setClinicians(db.clinicianDao().getAllClinicians());
                    });
                });
            });

            Toast.makeText(this, "Clinician saved: " + name, Toast.LENGTH_LONG).show();
        });

        // Initial load
        Executors.newSingleThreadExecutor().execute(() -> {
            clinicianAdapter.setClinicians(db.clinicianDao().getAllClinicians());
        });

        Button backToMainButton = findViewById(R.id.backToMainButton);
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClinicianFormActivity.this, MainActivity.class);
            startActivity(intent);
        });


    }
}


