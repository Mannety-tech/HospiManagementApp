package com.example.leedstrinity.hospimanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

import java.util.concurrent.Executors;

public class BookAppointmentActivity extends AppCompatActivity {

    private EditText patientNameEditText, dateEditText, timeEditText, reasonEditText;
    private Spinner specialtySpinner, specialistSpinner, clinicSpinner;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "hospital_db").build();

        // --- Bind views ---
        patientNameEditText = findViewById(R.id.editTextPatientName);
        dateEditText = findViewById(R.id.editTextDate);
        timeEditText = findViewById(R.id.editTextTime);
        reasonEditText = findViewById(R.id.editTextReason);
        specialtySpinner = findViewById(R.id.spinnerSpecialty);
        specialistSpinner = findViewById(R.id.spinnerSpecialist);
        clinicSpinner = findViewById(R.id.spinnerClinicLocation);

        Button submitAppointmentButton = findViewById(R.id.buttonSubmitAppointment);
        Button backToMainButton = findViewById(R.id.buttonBackToMain);

        // --- Set up adapters from strings.xml ---
        ArrayAdapter<CharSequence> specialtyAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.specialties_array,
                android.R.layout.simple_spinner_item
        );
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtySpinner.setAdapter(specialtyAdapter);

        ArrayAdapter<CharSequence> specialistAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.specialists_array,
                android.R.layout.simple_spinner_item
        );
        specialistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialistSpinner.setAdapter(specialistAdapter);

        ArrayAdapter<CharSequence> clinicAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.clinic_locations,
                android.R.layout.simple_spinner_item
        );
        clinicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clinicSpinner.setAdapter(clinicAdapter);

        // --- Date picker ---
        dateEditText.setOnClickListener(v -> {
            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            int year = calendar.get(java.util.Calendar.YEAR);
            int month = calendar.get(java.util.Calendar.MONTH);
            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    BookAppointmentActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateEditText.setText(selectedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // --- Time picker ---
        timeEditText.setOnClickListener(v -> {
            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
            int minute = calendar.get(java.util.Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    BookAppointmentActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        timeEditText.setText(selectedTime);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        // --- Submit appointment ---
        submitAppointmentButton.setOnClickListener(v -> {
            String patientName = patientNameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String time = timeEditText.getText().toString().trim();
            String reason = reasonEditText.getText().toString().trim();
            String specialty = specialtySpinner.getSelectedItem() != null
                    ? specialtySpinner.getSelectedItem().toString()
                    : "";
            String specialistName = specialistSpinner.getSelectedItem() != null
                    ? specialistSpinner.getSelectedItem().toString()
                    : "";
            String clinicLocation = clinicSpinner.getSelectedItem() != null
                    ? clinicSpinner.getSelectedItem().toString()
                    : "";

            if (specialty.equals("Select specialty...") ||
                    specialistName.equals("Select specialist...") ||
                    clinicLocation.equals("Select clinic...")) {
                Toast.makeText(this, "Please select valid specialty, specialist, and clinic", Toast.LENGTH_SHORT).show();
                return;
            }

            if (patientName.isEmpty() || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                long start = System.currentTimeMillis();
                long end = start + 3600000; // 1 hour later

                Appointment appointment = new Appointment(
                        patientName,
                        date,
                        time,
                        reason,
                        specialistName,
                        start,
                        end,
                        clinicLocation,
                        "BOOKED"
                );

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.appointmentDao().insert(appointment);
                    runOnUiThread(() -> Toast.makeText(this,
                            "Appointment saved for " + patientName +
                                    " with " + specialistName +
                                    " (" + specialty + ") at " + clinicLocation,
                            Toast.LENGTH_LONG).show());
                });
            }
        });

        // --- Back button ---
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookAppointmentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}
















