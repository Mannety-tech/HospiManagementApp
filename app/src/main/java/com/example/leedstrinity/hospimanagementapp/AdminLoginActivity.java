package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText etAdminEmail, etAdminPin, etStaffEmail, etStaffPin;
    private Button btnAdminLogin, btnStaffLogin, btnOpenAdminSetup, backToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login); // Ensure this matches your XML filename

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Admin login views
        etAdminEmail = findViewById(R.id.etAdminEmail);
        etAdminPin = findViewById(R.id.etAdminPin);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);

        // Staff login views
        etStaffEmail = findViewById(R.id.etStaffEmail);
        etStaffPin = findViewById(R.id.etStaffPin);
        btnStaffLogin = findViewById(R.id.btnStaffLogin);

        // Setup mode button
        btnOpenAdminSetup = findViewById(R.id.btnOpenAdminSetup);

        // Back to main button
        backToMainButton = findViewById(R.id.btnBackToMain); // Make sure this ID exists in your XML

        // Admin login logic
        btnAdminLogin.setOnClickListener(v -> {
            String email = etAdminEmail.getText().toString().trim();
            String pin = etAdminPin.getText().toString().trim();

            if (email.equals("admin@example.com") && pin.equals("1234")) {
                Toast.makeText(this, "Admin login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AdminPortalActivity.class));
            } else {
                Toast.makeText(this, "Invalid admin credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Staff login logic
        btnStaffLogin.setOnClickListener(v -> {
            String email = etStaffEmail.getText().toString().trim();
            String pin = etStaffPin.getText().toString().trim();

            if (!email.isEmpty() && !pin.isEmpty()) {
                Toast.makeText(this, "Staff login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, "Please enter staff credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Setup mode button (disabled by default)
        btnOpenAdminSetup.setOnClickListener(v -> {
            Toast.makeText(this, "Setup mode is currently disabled", Toast.LENGTH_SHORT).show();
        });

        // Back button click listener
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}




