package com.example.leedstrinity.hospimanagementapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

public class LandingActivity extends AppCompatActivity {

    private Button btnLogin, btnSignup, btnBiometricLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Match XML IDs
        btnLogin = findViewById(R.id.btnLoginPatient);   // login button
        btnSignup = findViewById(R.id.btnSignupPatient); // signup button
        btnBiometricLogin = findViewById(R.id.btnBiometricLogin); // biometric button

        // Login → unified LoginActivity
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Create Account → SignupActivity
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Biometric Login → check availability
        btnBiometricLogin.setOnClickListener(v -> {
            if (isEmulator()) {
                Toast.makeText(this, "Biometric not supported on emulator. Using password login.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LandingActivity.this, LoginActivity.class));
                return;
            }

            BiometricManager biometricManager = BiometricManager.from(this);
            int canAuthenticate = biometricManager.canAuthenticate(
                    BiometricManager.Authenticators.BIOMETRIC_WEAK |
                            BiometricManager.Authenticators.DEVICE_CREDENTIAL
            );

            if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                BiometricPrompt biometricPrompt = new BiometricPrompt(this,
                        ContextCompat.getMainExecutor(this),
                        new BiometricPrompt.AuthenticationCallback() {
                            @Override
                            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                                super.onAuthenticationSucceeded(result);
                                // Success → go to MainActivity or dashboard
                                Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onAuthenticationError(int errorCode, CharSequence errString) {
                                super.onAuthenticationError(errorCode, errString);
                                Toast.makeText(LandingActivity.this, "Biometric error: " + errString, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAuthenticationFailed() {
                                super.onAuthenticationFailed();
                                Toast.makeText(LandingActivity.this, "Biometric failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Biometric Login")
                        .setSubtitle("Use your face, fingerprint, or device PIN")
                        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK |
                                BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                        .setNegativeButtonText("Cancel")
                        .build();

                biometricPrompt.authenticate(promptInfo);

            } else {
                Toast.makeText(this, "Biometric not available. Using password login.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LandingActivity.this, LoginActivity.class));
            }
        });
    }

    // Emulator detection helper
    private boolean isEmulator() {
        String product = android.os.Build.PRODUCT;
        return product != null &&
                (product.contains("sdk") || product.contains("emulator") || product.contains("generic"));
    }
}








