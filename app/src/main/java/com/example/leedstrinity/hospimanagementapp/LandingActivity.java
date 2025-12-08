package com.example.leedstrinity.hospimanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

public class LandingActivity extends AppCompatActivity {

    private static final String CORRECT_PIN = "1234"; // replace with secure storage

    // Landing page UI
    private Button btnLogin, btnSignup, btnBiometricLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Step 1: show PIN gate first
        setContentView(R.layout.activity_pin_gate);

        EditText pinInput = findViewById(R.id.pinInput);
        Button btnSubmit = findViewById(R.id.btnSubmitPin);

        btnSubmit.setOnClickListener(v -> {
            String enteredPin = pinInput.getText().toString();
            if (CORRECT_PIN.equals(enteredPin)) {
                // Step 2: switch to landing layout once PIN is correct
                setContentView(R.layout.activity_landing);

                // Step 3: initialize landing page UI
                initLandingUI();
            } else {
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLandingUI() {
        // Buttons
        btnLogin = findViewById(R.id.btnLoginPatient);
        btnSignup = findViewById(R.id.btnSignupPatient);
        btnBiometricLogin = findViewById(R.id.btnBiometricLogin);

        // Login → LoginActivity
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Signup → SignupActivity
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Biometric login
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

    private boolean isEmulator() {
        String product = android.os.Build.PRODUCT;
        return product != null &&
                (product.contains("sdk") || product.contains("emulator") || product.contains("generic"));
    }
}












