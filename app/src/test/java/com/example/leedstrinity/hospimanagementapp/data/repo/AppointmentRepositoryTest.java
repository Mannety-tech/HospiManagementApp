package com.example.leedstrinity.hospimanagementapp.data.repo;


import com.example.leedstrinity.hospimanagementapp.AppDatabase;
import com.example.leedstrinity.hospimanagementapp.data.dao.AppointmentDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.network.dto.ApiClient;
import com.example.leedstrinity.hospimanagementapp.network.dto.AppointmentApi;
import com.example.leedstrinity.hospimanagementapp.network.dto.AppointmentDto;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AppointmentRepositoryTest {

    @Test
    public void testBookOrReschedule_successfulBooking() throws Exception {
        // Your test logic here
    }
}
