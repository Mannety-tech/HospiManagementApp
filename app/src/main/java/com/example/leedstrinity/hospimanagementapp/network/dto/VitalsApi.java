package com.example.leedstrinity.hospimanagementapp.network.dto;

import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VitalsApi {

    // Example: GET /vitals/latest?patientId=123
    @GET("/vitals/latest")
    Call<Vitals> getLatestVitals(@Query("patientId") String patientId);

    // Example: GET /vitals/{patientId}/history
    @GET("/vitals/{patientId}/history")
    Call<java.util.List<Vitals>> getVitalsHistory(@Path("patientId") String patientId);

    // Example: GET /vitals/{patientId}/specific?timestamp=...
    @GET("/vitals/{patientId}/specific")
    Call<Vitals> getVitalsAtTime(@Path("patientId") String patientId,
                                 @Query("timestamp") long timestamp);
}

