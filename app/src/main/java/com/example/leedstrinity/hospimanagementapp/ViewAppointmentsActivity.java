package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.AppointmentAdapter;

import java.util.List;

public class ViewAppointmentsActivity extends AppCompatActivity {

    private AppDatabase db;
    private long patientId;
    private RecyclerView recyclerView;
    private AppointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        // --- Get patientId from intent ---
        Intent intent = getIntent();
        patientId = intent.getLongExtra("patientId", -1);

        // --- Bind RecyclerView ---
        recyclerView = findViewById(R.id.recyclerViewAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AppointmentAdapter();
        recyclerView.setAdapter(adapter);

        // --- Get DB instance ---
        db = AppDatabase.getInstance(this);

        // --- Observe appointments for this patient ---
        db.appointmentDao().getAppointmentsForPatientLive(patientId)
                .observe(this, new Observer<List<Appointment>>() {
                    @Override
                    public void onChanged(List<Appointment> appointments) {
                        adapter.setAppointments(appointments);
                    }
                });
    }
}



