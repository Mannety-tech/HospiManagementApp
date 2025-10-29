package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class AdminPortalActivity extends AppCompatActivity {

    private EditText staffNameEditText, staffEmailEditText, adminPinEditText;
    private Spinner roleSpinner;
    private Button registerStaffButton, refreshListButton, backToMainButton;
    private RecyclerView staffRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_login); // Ensure this matches your XML layout

        // Initialize views
        staffNameEditText = findViewById(R.id.etStaffName);
        staffEmailEditText = findViewById(R.id.etStaffEmail);
        adminPinEditText = findViewById(R.id.etAdminSetupPin);
        roleSpinner = findViewById(R.id.spRole);
        registerStaffButton = findViewById(R.id.btnRegisterStaff);
        refreshListButton = findViewById(R.id.btnRefreshList);
        backToMainButton = findViewById(R.id.btnBackToMain); // ✅ Make sure this ID exists in your XML
        staffRecyclerView = findViewById(R.id.rvStaff);

        // Setup spinner with roles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.staff_roles, // Define this array in res/values/strings.xml
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Register staff logic
        registerStaffButton.setOnClickListener(v -> {
            String name = staffNameEditText.getText().toString().trim();
            String email = staffEmailEditText.getText().toString().trim();
            String role = roleSpinner.getSelectedItem().toString();
            String pin = adminPinEditText.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (role.equals("ADMIN") && pin.isEmpty()) {
                Toast.makeText(this, "Admin PIN is required for ADMIN role", Toast.LENGTH_SHORT).show();
                return;
            }

            // TODO: Save staff record to database or list
            Toast.makeText(this, "Staff registered: " + name + " (" + role + ")", Toast.LENGTH_LONG).show();
        });

        // Refresh staff list
        refreshListButton.setOnClickListener(v -> {
            // TODO: Refresh RecyclerView with latest staff data
            Toast.makeText(this, "Staff list refreshed", Toast.LENGTH_SHORT).show();
        });

        // Back to main screen
        backToMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminPortalActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}
