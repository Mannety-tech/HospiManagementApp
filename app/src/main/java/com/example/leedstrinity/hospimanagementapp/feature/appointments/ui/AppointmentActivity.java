package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui;


import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.security.auth.BiometricLoginCoordinator;
import com.example.leedstrinity.hospimanagementapp.security.auth.RbacPolicyEvaluator;

public class AppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // 🔐 RBAC check before biometric
        if (!RbacPolicyEvaluator.canViewAppointments(this)) {
            Toast.makeText(this, "Access denied. Please sign in with a permitted role.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // 🔒 Biometric authentication
        BiometricLoginCoordinator biometric = new BiometricLoginCoordinator();
        biometric.authenticate(this, new BiometricLoginCoordinator.Callback() {
            @Override
            public void onSuccess() {
                //  Load fragment only if fresh launch
                if (savedInstanceState == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.appointmentContainer, new AppointmentListFragment())
                            .commit();
                }
            }

            @Override
            public void onFailure(String reason) {
                Toast.makeText(AppointmentActivity.this, "Biometric required: " + reason, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}

