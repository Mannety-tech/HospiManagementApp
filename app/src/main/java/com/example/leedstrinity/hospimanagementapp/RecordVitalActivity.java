package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.util.Date;
import java.util.concurrent.Executors;

public class RecordVitalActivity extends AppCompatActivity {

    private EditText heartRateEditText, systolicEditText, diastolicEditText,
            temperatureEditText, respRateEditText;
    private long patientId;   // use long to match entity
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_vitals);

        // --- Get patientId ---
        patientId = getIntent().getLongExtra("patientId", -1);
        if (patientId == -1) {
            Toast.makeText(this, "No patient ID provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = AppDatabase.getInstance(getApplicationContext());

        // --- Bind views ---
        heartRateEditText = findViewById(R.id.editTextHeartRate);
        systolicEditText = findViewById(R.id.editTextSystolic);
        diastolicEditText = findViewById(R.id.editTextDiastolic);
        temperatureEditText = findViewById(R.id.editTextTemperature);
        respRateEditText = findViewById(R.id.editTextRespRate);

        Button saveButton = findViewById(R.id.buttonSaveVitals);
        saveButton.setOnClickListener(v -> {
            if (!validateInputs()) return;

            int heartRate = Integer.parseInt(heartRateEditText.getText().toString().trim());
            int systolic = Integer.parseInt(systolicEditText.getText().toString().trim());
            int diastolic = Integer.parseInt(diastolicEditText.getText().toString().trim());
            double temperature = Double.parseDouble(temperatureEditText.getText().toString().trim());
            int respRate = Integer.parseInt(respRateEditText.getText().toString().trim());

            //  Build Vitals object with setters
            Vitals vitals = new Vitals();
            vitals.setPatientId(patientId);
            vitals.setBloodPressure(systolic + "/" + diastolic);
            vitals.setHeartRate(String.valueOf(heartRate));
            vitals.setTemperature(String.valueOf(temperature));
            vitals.setOxygenLevel(String.valueOf(respRate));
            vitals.setRecordedAt(new Date()); // store as Date, handled by TypeConverter

            Executors.newSingleThreadExecutor().execute(() -> {
                db.vitalsDao().insert(vitals);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Vitals recorded successfully", Toast.LENGTH_SHORT).show();
                    finish(); // return to PatientVitalsActivity or Dashboard
                });
            });
        });
    }

    private boolean validateInputs() {
        if (heartRateEditText.getText().toString().trim().isEmpty()) {
            heartRateEditText.setError("Heart rate required");
            return false;
        }
        if (systolicEditText.getText().toString().trim().isEmpty()) {
            systolicEditText.setError("Systolic BP required");
            return false;
        }
        if (diastolicEditText.getText().toString().trim().isEmpty()) {
            diastolicEditText.setError("Diastolic BP required");
            return false;
        }
        if (temperatureEditText.getText().toString().trim().isEmpty()) {
            temperatureEditText.setError("Temperature required");
            return false;
        }
        if (respRateEditText.getText().toString().trim().isEmpty()) {
            respRateEditText.setError("Respiratory rate required");
            return false;
        }

        // Optional: add range checks
        int heartRate = Integer.parseInt(heartRateEditText.getText().toString().trim());
        if (heartRate < 30 || heartRate > 200) {
            heartRateEditText.setError("Heart rate out of range");
            return false;
        }

        double temperature = Double.parseDouble(temperatureEditText.getText().toString().trim());
        if (temperature < 30 || temperature > 45) {
            temperatureEditText.setError("Temperature out of range");
            return false;
        }

        return true;
    }
}




