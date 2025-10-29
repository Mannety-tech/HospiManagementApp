package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ClearSessionActivity extends AppCompatActivity {

    private Button clearSessionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_session);

        clearSessionButton = findViewById(R.id.buttonClearSession);

        clearSessionButton.setOnClickListener(v -> {
            // Add logic to clear session data here
            Toast.makeText(this, "Session cleared successfully", Toast.LENGTH_SHORT).show();
        });

    }
}

