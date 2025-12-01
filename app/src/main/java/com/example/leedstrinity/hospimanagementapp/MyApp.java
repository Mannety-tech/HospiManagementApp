package com.example.leedstrinity.hospimanagementapp;

import android.app.Application;
import com.example.leedstrinity.hospimanagementapp.security.auth.SecureDatabaseHelper;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Set encryption key once at app startup
        SecureDatabaseHelper.setKey("MySuperSecretPassphrase123");

        // You can also initialize other global things here (logging, analytics, etc.)
    }
}
