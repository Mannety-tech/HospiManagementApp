package com.example.leedstrinity.hospimanagementapp.network.dto;


import android.content.Context;
import okhttp3.OkHttpClient;
import com.example.leedstrinity.hospimanagementapp.network.dto.MockInterceptor;

public class NetworkModule {

    public static OkHttpClient provideClient(Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(new MockInterceptor(context)) // attach your mock interceptor
                .build();
    }
}

