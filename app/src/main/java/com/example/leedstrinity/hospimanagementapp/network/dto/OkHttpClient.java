package com.example.leedstrinity.hospimanagementapp.network.dto;

import okhttp3.CertificatePinner;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;

public class SecureHttpClientProvider {

    public static OkHttpClient getSecureClient() {
        // Pin your backend’s certificate (replace with your real domain + SHA-256 hash)
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add("api.yourdomain.com",
                        "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
                .build();

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .certificatePinner(certificatePinner)
                .build();
    }
}

