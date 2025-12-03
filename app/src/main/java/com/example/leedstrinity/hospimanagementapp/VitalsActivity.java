package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.VitalsViewModel;
import com.example.leedstrinity.hospimanagementapp.network.dto.RetrofitClient;
import com.example.leedstrinity.hospimanagementapp.network.dto.VitalsApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VitalsActivity extends AppCompatActivity {

    private TextView tvHeartRate, tvBloodPressure, tvTemperature, tvRespiratoryRate;
    private String patientId;
    private VitalsViewModel vitalsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

        // --- Bind views ---
        tvHeartRate = findViewById(R.id.tvHeartRate);
        tvBloodPressure = findViewById(R.id.tvBloodPressure);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvRespiratoryRate = findViewById(R.id.tvRespiratoryRate);

        // --- Get patientId from Intent ---
        patientId = getIntent().getStringExtra("patientId");

        // --- Init ViewModel ---
        vitalsViewModel = new ViewModelProvider(this).get(VitalsViewModel.class);

        // --- Observe latest vitals from DB ---
        vitalsViewModel.getLatestVitalsForPatient(patientId).observe(this, latest -> {
            if (latest != null) {
                updateUI(
                        latest.getHeartRate(),
                        latest.getSystolicBP(),
                        latest.getDiastolicBP(),
                        latest.getTemperature(),
                        latest.getRespiratoryRate()
                );
            }
        });

        // --- Try to fetch vitals from API ---
        fetchVitalsFromApi();
    }

    private void fetchVitalsFromApi() {
        VitalsApi vitalsApi = RetrofitClient.getVitalsApi(this);
        vitalsApi.getLatestVitals(patientId).enqueue(new Callback<Vitals>() {
            @Override
            public void onResponse(Call<Vitals> call, Response<Vitals> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Vitals vitals = response.body();
                    updateUI(
                            vitals.getHeartRate(),
                            vitals.getSystolicBP(),
                            vitals.getDiastolicBP(),
                            vitals.getTemperature(),
                            vitals.getRespiratoryRate()
                    );

                    // Save to DB via ViewModel
                    vitalsViewModel.insert(vitals);
                } else {
                    loadFromIntent(); // fallback
                }
            }

            @Override
            public void onFailure(Call<Vitals> call, Throwable t) {
                loadFromIntent(); // fallback
            }
        });
    }

    private void loadFromIntent() {
        float temperature = getIntent().getFloatExtra("temperature", 0f);
        String bloodPressure = getIntent().getStringExtra("bloodPressure");
        int heartRate = getIntent().getIntExtra("heartRate", 0);
        int respiratoryRate = getIntent().getIntExtra("respiratoryRate", 0);

        tvTemperature.setText("Temperature: " + temperature + " °C");
        tvBloodPressure.setText("Blood Pressure: " + bloodPressure);
        tvHeartRate.setText("Heart Rate: " + heartRate + " bpm");
        tvRespiratoryRate.setText("Respiratory Rate: " + respiratoryRate + " breaths/min");

        // Save fallback vitals to DB too
        Vitals vitals = new Vitals(patientId, heartRate, 120, 80, temperature, respiratoryRate);
        vitalsViewModel.insert(vitals);
    }

    private void updateUI(int heartRate, int systolicBP, int diastolicBP,
                          double temperature, int respiratoryRate) {
        tvHeartRate.setText("Heart Rate: " + heartRate + " bpm");
        tvBloodPressure.setText("Blood Pressure: " + systolicBP + "/" + diastolicBP + " mmHg");
        tvTemperature.setText("Temperature: " + temperature + " °C");
        tvRespiratoryRate.setText("Respiratory Rate: " + respiratoryRate + " breaths/min");
    }
}






