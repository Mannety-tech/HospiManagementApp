package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PatientRecordActivity extends AppCompatActivity {

    private EditText nameEditText, ageEditText, genderEditText, contactEditText, notesEditText;
    private Button saveRecordButton, backToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record);

        nameEditText = findViewById(R.id.editTextPatientName);
        ageEditText = findViewById(R.id.editTextAge);
        genderEditText = findViewById(R.id.editTextGender);
        contactEditText = findViewById(R.id.editTextContact);
        notesEditText = findViewById(R.id.editTextMedicalNotes);
        saveRecordButton = findViewById(R.id.buttonSaveRecord);
        backToMainButton = findViewById(R.id.buttonBackToMain);

        saveRecordButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String age = ageEditText.getText().toString().trim();
            String gender = genderEditText.getText().toString().trim();
            String contact = contactEditText.getText().toString().trim();
            String notes = notesEditText.getText().toString().trim();

            if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || contact.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Patient record saved for " + name, Toast.LENGTH_LONG).show();
                // You can add logic here to save to database or file
            }
        });

        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(PatientRecordActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}


