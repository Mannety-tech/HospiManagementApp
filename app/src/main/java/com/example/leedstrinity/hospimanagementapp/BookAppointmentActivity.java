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

import java.util.List;
import java.util.concurrent.Executors;

public class BookAppointmentActivity extends AppCompatActivity {

    private EditText patientNameEditText, dateEditText, timeEditText, reasonEditText;
    private Spinner specialtySpinner, doctorSpinner, clinicSpinner;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // Room DB instance
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "hospital_db").build();

        patientNameEditText = findViewById(R.id.editTextPatientName);
        dateEditText = findViewById(R.id.editTextDate);
        timeEditText = findViewById(R.id.editTextTime);
        reasonEditText = findViewById(R.id.editTextReason);
        specialtySpinner = findViewById(R.id.spinnerSpecialty);
        doctorSpinner = findViewById(R.id.spinnerDoctorName);
        clinicSpinner = findViewById(R.id.spinnerClinicLocation);

        Button submitAppointmentButton = findViewById(R.id.buttonSubmitAppointment);
        Button backToMainButton = findViewById(R.id.buttonBackToMain);

        // ✅ Observe specialties dynamically from Staff table
        db.staffDao().getAllSpecialtiesLive().observe(this, specialties -> {
            ArrayAdapter<String> specialtyAdapter = new ArrayAdapter<>(
                    BookAppointmentActivity.this,
                    android.R.layout.simple_spinner_item,
                    specialties
            );
            specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            specialtySpinner.setAdapter(specialtyAdapter);
        });

        // ✅ Observe all specialist names (if you want a direct list without filtering)
        db.staffDao().getAllSpecialistNamesLive().observe(this, specialistNames -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    BookAppointmentActivity.this,
                    android.R.layout.simple_spinner_item,
                    specialistNames
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            doctorSpinner.setAdapter(adapter);
        });

        // ✅ When specialty changes, update doctor list
        specialtySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedSpecialty = specialtySpinner.getSelectedItem().toString();

                db.staffDao().getDoctorNamesBySpecialty(selectedSpecialty).observe(BookAppointmentActivity.this, doctorNames -> {
                    List<String> displayList = (doctorNames == null || doctorNames.isEmpty())
                            ? java.util.Collections.singletonList("No doctors available")
                            : doctorNames;

                    ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(
                            BookAppointmentActivity.this,
                            android.R.layout.simple_spinner_item,
                            displayList
                    );
                    doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    doctorSpinner.setAdapter(doctorAdapter);

                    // Disable submit if no doctors
                    submitAppointmentButton.setEnabled(!(doctorNames == null || doctorNames.isEmpty()));
                });
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });

        // ✅ Observe clinic locations dynamically
        db.clinicDao().getAllClinicNamesLive().observe(this, clinicNames -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    BookAppointmentActivity.this,
                    android.R.layout.simple_spinner_item,
                    clinicNames
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            clinicSpinner.setAdapter(adapter);
        });

        // Date picker
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

        // Time picker
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

        // Submit Button
        submitAppointmentButton.setOnClickListener(v -> {
            String name = patientNameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String time = timeEditText.getText().toString().trim();
            String reason = reasonEditText.getText().toString().trim();
            String doctorName = doctorSpinner.getSelectedItem() != null
                    ? doctorSpinner.getSelectedItem().toString()
                    : "";
            String specialty = specialtySpinner.getSelectedItem() != null
                    ? specialtySpinner.getSelectedItem().toString()
                    : "";
            String clinicLocation = clinicSpinner.getSelectedItem() != null
                    ? clinicSpinner.getSelectedItem().toString()
                    : "";

            if (doctorName.equals("No doctors available")) {
                Toast.makeText(this, "Please choose a specialty with available doctors", Toast.LENGTH_SHORT).show();
                return;
            }

            if (name.isEmpty() || date.isEmpty() || time.isEmpty() || reason.isEmpty()
                    || doctorName.isEmpty() || specialty.isEmpty() || clinicLocation.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                long start = System.currentTimeMillis();
                long end = start + 3600000; // 1 hour later

                // ⚠️ Make sure Appointment entity has a clinicLocation field
                Appointment appointment = new Appointment(name, date, time, reason, doctorName, start, end, clinicLocation);

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.appointmentDao().insert(appointment);
                    runOnUiThread(() -> Toast.makeText(this,
                            "Appointment saved for " + name + " with " + doctorName + " (" + specialty + ") at " + clinicLocation,
                            Toast.LENGTH_LONG).show());
                });
            }
        });

        // Back Button
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookAppointmentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}










