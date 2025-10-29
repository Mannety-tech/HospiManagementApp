package com.example.leedstrinity.hospimanagementapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AppointmentActivity extends AppCompatActivity {

    private EditText patientNameEditText, dateEditText, timeEditText, reasonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        patientNameEditText = findViewById(R.id.editTextPatientName);
        dateEditText = findViewById(R.id.editTextDate);
        timeEditText = findViewById(R.id.editTextTime);
        reasonEditText = findViewById(R.id.editTextReason);
        Button submitAppointmentButton = findViewById(R.id.buttonSubmitAppointment);
        Button backToMainButton = findViewById(R.id.buttonBackToMain);

        submitAppointmentButton.setOnClickListener(v -> {
            String name = patientNameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String time = timeEditText.getText().toString().trim();
            String reason = reasonEditText.getText().toString().trim();

            if (name.isEmpty() || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
                Toast.makeText(AppointmentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppointmentActivity.this, "Appointment booked for " + name, Toast.LENGTH_LONG).show();
                // You can add logic here to save to database or send to server
            }
        });

        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(AppointmentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

}
