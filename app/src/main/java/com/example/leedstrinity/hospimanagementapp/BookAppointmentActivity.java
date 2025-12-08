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

import java.util.Calendar;
import java.util.concurrent.Executors;

public class BookAppointmentActivity extends AppCompatActivity {

    private EditText patientNameEditText, dateEditText, timeEditText, reasonEditText;
    private Spinner specialtySpinner, specialistSpinner, clinicSpinner;
    private AppDatabase db;

    // Patient details passed from PatientDashboardActivity
    private long patientId;
    private String patientName, patientEmail, nhsNumber;

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
        Button btnGoBack = findViewById(R.id.btnGoBack);

        // --- Get patient details from intent ---
        Intent intent = getIntent();
        patientId = intent.getLongExtra("patientId", -1);
        patientName = intent.getStringExtra("patientName");
        patientEmail = intent.getStringExtra("patientEmail");
        nhsNumber = intent.getStringExtra("nhsNumber");

        // --- Set up adapters ---
        ArrayAdapter<CharSequence> specialtyAdapter = ArrayAdapter.createFromResource(
                this, R.array.specialties_array, android.R.layout.simple_spinner_item);
        specialtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialtySpinner.setAdapter(specialtyAdapter);

        ArrayAdapter<CharSequence> specialistAdapter = ArrayAdapter.createFromResource(
                this, R.array.specialists_array, android.R.layout.simple_spinner_item);
        specialistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialistSpinner.setAdapter(specialistAdapter);

        ArrayAdapter<CharSequence> clinicAdapter = ArrayAdapter.createFromResource(
                this, R.array.clinic_locations, android.R.layout.simple_spinner_item);
        clinicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clinicSpinner.setAdapter(clinicAdapter);

        // --- Date picker ---
        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

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
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

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
            String patientNameInput = patientNameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String time = timeEditText.getText().toString().trim();
            String reason = reasonEditText.getText().toString().trim();
            String specialty = specialtySpinner.getSelectedItem() != null
                    ? specialtySpinner.getSelectedItem().toString() : "";
            String specialistName = specialistSpinner.getSelectedItem() != null
                    ? specialistSpinner.getSelectedItem().toString() : "";
            String clinicLocation = clinicSpinner.getSelectedItem() != null
                    ? clinicSpinner.getSelectedItem().toString() : "";

            if (specialty.equals("Select specialty...") ||
                    specialistName.equals("Select specialist...") ||
                    clinicLocation.equals("Select clinic...")) {
                Toast.makeText(this, "Please select valid specialty, specialist, and clinic", Toast.LENGTH_SHORT).show();
                return;
            }

            if (patientNameInput.isEmpty() || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                long start = System.currentTimeMillis();
                long end = start + 3600000; // 1 hour later

                // ✅ Use setters with no-arg constructor
                Appointment appointment = new Appointment();
                appointment.setPatientId(patientId);
                appointment.setPatientName(patientNameInput);
                appointment.setClinicianName(null);
                appointment.setClinicLocation(clinicLocation);
                appointment.setReason(reason);
                appointment.setStatus("BOOKED");
                appointment.setDate(date);
                appointment.setStartTimeMillis(start);
                appointment.setEndTimeMillis(end);
                appointment.setSpecialistName(specialistName);

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.appointmentDao().insert(appointment);
                    runOnUiThread(() -> Toast.makeText(this,
                            "Appointment saved for " + patientNameInput +
                                    " with " + specialistName +
                                    " (" + specialty + ") at " + clinicLocation,
                            Toast.LENGTH_LONG).show());
                });
            }
        });

        // --- Go Back button ---
        btnGoBack.setOnClickListener(v -> {
            Intent backIntent = new Intent(BookAppointmentActivity.this, PatientDashboardActivity.class);
            backIntent.putExtra("patientId", patientId);
            backIntent.putExtra("patientName", patientName);
            backIntent.putExtra("patientEmail", patientEmail);
            backIntent.putExtra("nhsNumber", nhsNumber);
            startActivity(backIntent);
            finish();
        });
    }
}



















