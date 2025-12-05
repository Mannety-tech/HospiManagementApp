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
        // Use the constructor that matches your entity (9 args)
        Appointment input = new Appointment(
                "John Doe",      // patientName
                "2025-11-25",    // date
                "10:00",         // time
                "Checkup",       // reason
                "Dr. Smith",     // specialistName
                1000L,           // startTimeMillis
                2000L,           // endTimeMillis
                "Clinic A",      // clinicLocation
                "BOOKED"         // status
        );

        when(mockRepo.bookOrReschedule(input)).thenReturn(input);

        Appointment result = useCase.execute(input);

        assertEquals("Dr. Smith", result.getSpecialistName());
        assertEquals("BOOKED", result.getStatus());
        verify(mockRepo).bookOrReschedule(input);
    }
}

