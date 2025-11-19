package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AppointmentListActivity extends AppCompatActivity {

    private RecyclerView appointmentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);

        appointmentRecyclerView = findViewById(R.id.rvAppointments);
        appointmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Connect to Room database and load appointments
        // For now, you can set a dummy adapter or leave it empty
    }
}

