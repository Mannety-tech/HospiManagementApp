package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.AppointmentAdapter;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.VitalsAdapter;

import java.util.List;

public class PatientDetailsActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView tvPatientInfo;
    private RecyclerView rvAppointments, rvVitals;

    private AppointmentAdapter appointmentAdapter;
    private VitalsAdapter vitalsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure you have res/layout/activity_patient_details.xml
        setContentView(R.layout.activity_patient_details);

        tvPatientInfo = findViewById(R.id.tvPatientInfo);
        rvAppointments = findViewById(R.id.rvAppointments);
        rvVitals = findViewById(R.id.rvVitals);

        rvAppointments.setLayoutManager(new LinearLayoutManager(this));
        rvVitals.setLayoutManager(new LinearLayoutManager(this));

        appointmentAdapter = new AppointmentAdapter();
        vitalsAdapter = new VitalsAdapter();

        rvAppointments.setAdapter(appointmentAdapter);
        rvVitals.setAdapter(vitalsAdapter);

        db = AppDatabase.getInstance(this);

        long patientId = getIntent().getLongExtra("patientId", -1);

        if (patientId != -1) {
            // Patient info
            new Thread(() -> {
                Patient patient = db.patientDao().findById(patientId);
                runOnUiThread(() -> {
                    if (patient != null) {
                        tvPatientInfo.setText(
                                "Name: " + patient.getName() + "\n" +
                                        "Email: " + patient.getEmail() + "\n" +
                                        "NHS No: " + patient.getNhsNumber()
                        );
                    }
                });
            }).start();

            // Appointments
            db.appointmentDao().getAppointmentsForPatient(patientId)
                    .observe(this, new Observer<List<Appointment>>() {
                        @Override
                        public void onChanged(List<Appointment> appointments) {
                            appointmentAdapter.setAppointments(appointments);
                        }
                    });

            // Vitals
            db.vitalsDao().getVitalsForPatient(patientId)
                    .observe(this, new Observer<List<Vitals>>() {
                        @Override
                        public void onChanged(List<Vitals> vitals) {
                            vitalsAdapter.setVitals(vitals);
                        }
                    });
        }
    }
}


