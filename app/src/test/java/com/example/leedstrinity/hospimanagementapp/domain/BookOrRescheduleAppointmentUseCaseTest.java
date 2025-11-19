package com.example.leedstrinity.hospimanagementapp.domain;


import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BookOrRescheduleAppointmentUseCaseTest {

    private AppointmentRepository mockRepo;
    private BookOrRescheduleAppointmentUseCase useCase;

    @Before
    public void setup() {
        mockRepo = mock(AppointmentRepository.class);
        useCase = new BookOrRescheduleAppointmentUseCase(mockRepo);
    }

    @Test
    public void testExecute_returnsBookedAppointment() throws Exception {
        Appointment input = new Appointment("1234567890", 1000L, 2000L, 101L, "Dr. Smith", "Cardiology", "BOOKED");

        when(mockRepo.bookOrReschedule(input)).thenReturn(input);

        Appointment result = useCase.execute(input);

        assertEquals("Dr. Smith", result.clinicianName);
        assertEquals("BOOKED", result.status);
        verify(mockRepo).bookOrReschedule(input);
    }
}

