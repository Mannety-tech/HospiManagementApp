package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.VitalsAdapter;

import java.util.List;

public class PatientVitalsActivity extends AppCompatActivity {

    private AppDatabase db;
    private long patientId;
    private RecyclerView recyclerView;
    private VitalsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_vitals);

        // --- Get patientId from intent ---
        Intent intent = getIntent();
        patientId = intent.getLongExtra("patientId", -1);

        // --- Bind RecyclerView ---
        recyclerView = findViewById(R.id.recyclerViewVitals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VitalsAdapter(vitals -> {
            // On item click → open detail screen
            Intent detailIntent = new Intent(PatientVitalsActivity.this, VitalsDetailActivity.class);
            detailIntent.putExtra("bp", vitals.getBloodPressure());
            detailIntent.putExtra("hr", vitals.getHeartRate());
            detailIntent.putExtra("temp", vitals.getTemperature());
            detailIntent.putExtra("oxygen", vitals.getOxygenLevel());
            detailIntent.putExtra("recordedAt", vitals.getFormattedRecordedAt());
            startActivity(detailIntent);
        });

        recyclerView.setAdapter(adapter);

        // --- Get DB instance ---
        db = AppDatabase.getInstance(this);

        // --- Observe vitals for this patient ---
        db.vitalsDao().getVitalsForPatientLive(patientId)
                .observe(this, new Observer<List<Vitals>>() {
                    @Override
                    public void onChanged(List<Vitals> vitalsList) {
                        adapter.setVitals(vitalsList);
                    }
                });
    }
}




