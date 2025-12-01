package com.example.leedstrinity.hospimanagementapp.network.dto;

import android.content.Context;
import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * MockInterceptor simulates API responses by serving JSON from assets.
 * Useful for offline development, testing, and UI prototyping.
 */
public class MockInterceptor implements Interceptor {

    private static final String TAG = "MockInterceptor";
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
            // --- Match endpoints ---
            if (path.endsWith("/appointments/today")) {
                jsonResponse = readAsset("mock/appointments_today.json");
            } else if (path.endsWith("/appointments/bookOrReschedule")) {
                jsonResponse = readAsset("mock/booking_success.json");
            } else if (path.endsWith("/staff/login")) {
                jsonResponse = readAsset("mock/staff_login.json");
            } else if (path.endsWith("/patients/login")) {
                jsonResponse = readAsset("mock/patient_login.json");
            } else {
                Log.w(TAG, "No mock found for path: " + path);
                jsonResponse = "{\"error\":\"no mock available\"}";
            }

            // --- Build success response ---
            return new Response.Builder()
                    .code(200)
                    .message("OK")
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create(jsonResponse, MediaType.get("application/json")))
                    .build();

        } catch (Exception e) {
            Log.e(TAG, "MockInterceptor failure for path: " + path, e);

            // --- Build error response ---
            return new Response.Builder()
                    .code(500)
                    .message("Mock failure")
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(ResponseBody.create("{\"error\":\"mock failure\"}", MediaType.get("application/json")))
                    .build();
        }
    }


    private String readAsset(String assetPath) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(appContext.getAssets().open(assetPath), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n'); // buffer with line breaks
            }
        }
        return builder.toString();
    }
}


