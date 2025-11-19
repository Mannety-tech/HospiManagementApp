

package com.example.leedstrinity.hospimanagementapp.domain;

import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GetTodaysAppointmentsUseCaseTest {

    private AppointmentRepository mockRepo;
    private GetTodaysAppointmentsUseCase useCase;

    @Before
    public void setup() {
        mockRepo = mock(AppointmentRepository.class);
        useCase = new GetTodaysAppointmentsUseCase(mockRepo); // requires constructor injection
    }

    @Test
    public void testExecute_returnsAppointmentsForToday() throws Exception {
        String clinic = "Cardiology";

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        long end = cal.getTimeInMillis();

        Appointment a1 = new Appointment("1234567890", start, end, 101, "Dr. Smith", clinic, "BOOKED");
        List<Appointment> expected = Arrays.asList(a1);

        when(mockRepo.getTodaysAppointments(clinic, start, end)).thenReturn(expected);

        List<Appointment> result = useCase.execute(clinic);

        assertEquals(1, result.size());
        assertEquals("Dr. Smith", result.get(0).clinicianName);
        verify(mockRepo).getTodaysAppointments(clinic, start, end);
    }
}
