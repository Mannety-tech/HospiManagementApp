package com.example.leedstrinity.hospimanagementapp.security.auth;



import android.content.Context;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class BiometricLoginCoordinator {

    public interface Callback {
        void onSuccess();
        void onFailure(String reason);
    }

    public void authenticate(FragmentActivity activity, Callback callback) {
        BiometricManager biometricManager = BiometricManager.from(activity);
        int authStatus = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

        if (authStatus != BiometricManager.BIOMETRIC_SUCCESS) {
            callback.onFailure("Biometrics unavailable");
            return;
        }

        Executor executor = ContextCompat.getMainExecutor(activity);

        BiometricPrompt.AuthenticationCallback authCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                callback.onSuccess();
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                callback.onFailure(errString.toString());
            }

            @Override
            public void onAuthenticationFailed() {
                callback.onFailure("Authentication failed");
            }
        };

        BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, authCallback);

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Confirm your identity")
                .setSubtitle("Access Appointments")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}



