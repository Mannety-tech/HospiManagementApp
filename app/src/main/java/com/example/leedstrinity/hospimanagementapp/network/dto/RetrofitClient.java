package com.example.leedstrinity.hospimanagementapp.network.dto;

import android.content.Context;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    // Generic builder for any API interface
    public static <T> T createService(Context context, Class<T> serviceClass) {
        if (retrofit == null) {
            // Configure certificate pinning
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add("api.yourdomain.com",
                            "sha256/RhKPhUt34qSWDMKCAd37/UUNUV0tOhZ2EYbpRKGPDbo=")
                    .build();

            OkHttpClient client = new OkHttpClient.Builder()
                    .certificatePinner(certificatePinner)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.yourdomain.com/") // your backend domain
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(serviceClass);
    }

    // Convenience method for Appointment API
    public static AppointmentApi getAppointmentApi(Context context) {
        return createService(context, AppointmentApi.class);
    }

    // Example: add vitals API later
    public static VitalsApi getVitalsApi(Context context) {
        return createService(context, VitalsApi.class);
    }
}



