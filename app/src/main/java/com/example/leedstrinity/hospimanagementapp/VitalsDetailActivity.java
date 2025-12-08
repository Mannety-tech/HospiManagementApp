package com.example.leedstrinity.hospimanagementapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VitalsDetailActivity extends AppCompatActivity {

    private TextView tvBP, tvHR, tvTemp, tvOxygen, tvRecorded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals_detail);

        tvBP = findViewById(R.id.tvBPDetail);
        tvHR = findViewById(R.id.tvHRDetail);
        tvTemp = findViewById(R.id.tvTempDetail);
        tvOxygen = findViewById(R.id.tvOxygenDetail);
        tvRecorded = findViewById(R.id.tvRecordedDetail);

        // Get extras from intent
        String bp = getIntent().getStringExtra("bp");
        String hr = getIntent().getStringExtra("hr");
        String temp = getIntent().getStringExtra("temp");
        String oxygen = getIntent().getStringExtra("oxygen");
        String recordedAt = getIntent().getStringExtra("recordedAt");

        tvBP.setText("Blood Pressure: " + bp);
        tvHR.setText("Heart Rate: " + hr);
        tvTemp.setText("Temperature: " + temp);
        tvOxygen.setText("Oxygen Level: " + oxygen);
        tvRecorded.setText("Recorded At: " + recordedAt);
    }
}

