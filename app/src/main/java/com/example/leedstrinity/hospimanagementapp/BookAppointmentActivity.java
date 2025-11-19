package com.example.leedstrinity.hospimanagementapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.AppointmentActivity;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    private EditText patientNameEditText, dateEditText, timeEditText, reasonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        patientNameEditText = findViewById(R.id.editTextPatientName);
        dateEditText = findViewById(R.id.editTextDate);
        timeEditText = findViewById(R.id.editTextTime);
        reasonEditText = findViewById(R.id.editTextReason);
        Button submitAppointmentButton = findViewById(R.id.buttonSubmitAppointment);
        Button backToMainButton = findViewById(R.id.buttonBackToMain);

        // 📅 Date Picker
        dateEditText.setFocusable(false);
        dateEditText.setClickable(true);
        dateEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    BookAppointmentActivity.this,
                    (DatePicker view, int selectedYear, int selectedMonth, int selectedDay) -> {
                        String formattedDate = String.format("%02d-%02d-%04d", selectedDay, selectedMonth + 1, selectedYear);
                        dateEditText.setText(formattedDate);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // ⏰ Time Picker
        timeEditText.setFocusable(false);
        timeEditText.setClickable(true);
        timeEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    BookAppointmentActivity.this,
                    (TimePicker view, int selectedHour, int selectedMinute) -> {
                        String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        timeEditText.setText(formattedTime);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });

        //  Submit Button
        submitAppointmentButton.setOnClickListener(v -> {
            String name = patientNameEditText.getText().toString().trim();
            String date = dateEditText.getText().toString().trim();
            String time = timeEditText.getText().toString().trim();
            String reason = reasonEditText.getText().toString().trim();

            if (name.isEmpty() || date.isEmpty() || time.isEmpty() || reason.isEmpty()) {
                Toast.makeText(BookAppointmentActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BookAppointmentActivity.this, "Appointment booked for " + name, Toast.LENGTH_LONG).show();
                // You can add logic here to save to database or send to server
            }
        });

        // 🔙 Back Button
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(BookAppointmentActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}
