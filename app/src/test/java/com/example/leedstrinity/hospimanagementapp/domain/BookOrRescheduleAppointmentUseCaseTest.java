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
        // ✅ Use the constructor that matches your entity
        Appointment input = new Appointment(
                "John Doe",      // patientName
                "2025-11-25",    // date
                "10:00",         // time
                "Checkup",       // reason
                "Dr. Smith",     // doctorName
                1000L,           // start
                2000L,           // end
                "BOOKED"         // status
        );

        when(mockRepo.bookOrReschedule(input)).thenReturn(input);

        Appointment result = useCase.execute(input);

        assertEquals("Dr. Smith", result.doctorName);
        assertEquals("BOOKED", result.status);
        verify(mockRepo).bookOrReschedule(input);
    }
}

