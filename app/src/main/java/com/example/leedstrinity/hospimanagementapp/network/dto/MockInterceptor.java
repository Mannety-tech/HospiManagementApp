package com.example.leedstrinity.hospimanagementapp.network.dto;


import android.content.Context;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MockInterceptor implements Interceptor {

    private final Context appContext;

    public MockInterceptor(Context context) {
        this.appContext = context.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        String path = request.url().encodedPath();
        String jsonResponse = "{}";

        try {
            if (path.endsWith("/appointments/today")) {
                jsonResponse = readAsset("mock/appointments_today.json");
            } else if (path.endsWith("/appointments/bookOrReschedule")) {
                jsonResponse = readAsset("mock/booking_success.json");
            }

            return new Response.Builder()
                    .code(200)
                    .message("OK")
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(jsonResponse, MediaType.get("application/json")))
                    .build();

        } catch (Exception e) {
            return new Response.Builder()
                    .code(500)
                    .message("Mock failure")
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create("{\"error\":\"mock\"}", MediaType.get("application/json")))
                    .build();
        }
    }

    private String readAsset(String assetPath) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(appContext.getAssets().open(assetPath), StandardCharsets.UTF_8)
        );
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }
}

