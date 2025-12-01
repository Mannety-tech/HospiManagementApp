package com.example.leedstrinity.hospimanagementapp.network.dto;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    // Generic builder for any API interface
    public static <T> T createService(Context context, Class<T> serviceClass) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new MockInterceptor(context)) // mock responses
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://mock.api/") // must be valid, even if mocked
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


