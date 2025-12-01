package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.data.entities.Staff;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private EditText etEmployeeNumber, etNhsNumber;
    private Spinner spSpecialty, spClinic;
    private RadioButton rbPatient, rbStaff;
    private Button btnSignup, btnBack;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // --- Bind views ---
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etEmployeeNumber = findViewById(R.id.etEmployeeNumber);
        etNhsNumber = findViewById(R.id.etNhsNumber);
        spSpecialty = findViewById(R.id.spSpecialty);
        spClinic = findViewById(R.id.spClinic);
        rbPatient = findViewById(R.id.rbPatient);
        rbStaff = findViewById(R.id.rbStaff);
        btnSignup = findViewById(R.id.btnSignup);
        btnBack = findViewById(R.id.btnBack);

        db = AppDatabase.getInstance(this);

        // --- Back button ---
        btnBack.setOnClickListener(v -> finish());

        // --- Initialize spinners with defaults from strings.xml ---
        ArrayAdapter<CharSequence> defaultSpecialtyAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.specialties_array,
                android.R.layout.simple_spinner_item
        );
        defaultSpecialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpecialty.setAdapter(defaultSpecialtyAdapter);

        ArrayAdapter<CharSequence> defaultClinicAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.clinic_locations,
                android.R.layout.simple_spinner_item
        );
        defaultClinicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClinic.setAdapter(defaultClinicAdapter);

        // --- Toggle visibility ---
        rbPatient.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etNhsNumber.setVisibility(View.VISIBLE);
                etEmployeeNumber.setVisibility(View.GONE);
                spSpecialty.setVisibility(View.GONE);
                spClinic.setVisibility(View.GONE);
            }
        });

        rbStaff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etNhsNumber.setVisibility(View.GONE);
                etEmployeeNumber.setVisibility(View.VISIBLE);
                spSpecialty.setVisibility(View.VISIBLE);
                spClinic.setVisibility(View.VISIBLE);
            }
        });

        // --- Override specialties with DB values when available ---
        db.staffDao().getAllSpecialtiesLive().observe(this, specialties -> {
            if (specialties != null && !specialties.isEmpty()) {
                List<String> merged = new ArrayList<>();
                merged.add("Select specialty...");
                merged.addAll(specialties);

                ArrayAdapter<String> specialtyAdapter = new ArrayAdapter<>(
                        SignupActivity.this,
                        android.R.layout.simple_spinner_item,
                        merged
                );
                specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spSpecialty.setAdapter(specialtyAdapter);
            }
        });

        // --- Override clinics when specialty changes ---
        spSpecialty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpecialty = spSpecialty.getSelectedItem().toString();

                db.staffDao().getClinicsBySpecialty(selectedSpecialty).observe(SignupActivity.this, clinicNames -> {
                    if (clinicNames != null && !clinicNames.isEmpty()) {
                        List<String> merged = new ArrayList<>();
                        merged.add("Select clinic...");
                        merged.addAll(clinicNames);

                        ArrayAdapter<String> clinicAdapter = new ArrayAdapter<>(
                                SignupActivity.this,
                                android.R.layout.simple_spinner_item,
                                merged
                        );
                        clinicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spClinic.setAdapter(clinicAdapter);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // --- Signup button logic (unchanged) ---
        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (rbPatient.isChecked()) {
                String nhsNumber = etNhsNumber.getText().toString().trim();
                if (nhsNumber.isEmpty()) {
                    etNhsNumber.setError("NHS number is required");
                    return;
                }

                Patient newPatient = new Patient(
                        name, "", "", "", "", nhsNumber, email, password, "patient"
                );

                new Thread(() -> {
                    db.patientDao().insertPatient(newPatient);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Patient signup successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    });
                }).start();

            } else if (rbStaff.isChecked()) {
                String empNo = etEmployeeNumber.getText().toString().trim();
                String specialty = (spSpecialty.getSelectedItem() != null)
                        ? spSpecialty.getSelectedItem().toString().trim()
                        : "";
                String clinicLocation = (spClinic.getSelectedItem() != null)
                        ? spClinic.getSelectedItem().toString().trim()
                        : "";

                if (empNo.isEmpty()) {
                    etEmployeeNumber.setError("Employee number is required");
                    return;
                }

                if (specialty.equals("Select specialty...") || specialty.isEmpty()) {
                    Toast.makeText(this, "Please select a valid specialty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (clinicLocation.equals("Select clinic...") || clinicLocation.isEmpty()) {
                    Toast.makeText(this, "Please select a clinic", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Split full name into first and last
                String[] parts = name.split(" ", 2);
                String firstName = parts.length > 0 ? parts[0] : name;
                String lastName = parts.length > 1 ? parts[1] : "";

                Staff newStaff = new Staff(
                        firstName,
                        lastName,
                        empNo,
                        specialty,
                        "Doctor",
                        clinicLocation,
                        "",
                        email,
                        password
                );

                new Thread(() -> {
                    db.staffDao().insert(newStaff);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Staff signup successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        finish();
                    });
                }).start();
            }
        });
    }
}















