package com.example.leedstrinity.hospimanagementapp.network.dto;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static AppointmentApi getAppointmentApi(Context context) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new MockInterceptor(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://mock.api/") // Replace with your actual mock base URL
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(AppointmentApi.class);
    }
}

