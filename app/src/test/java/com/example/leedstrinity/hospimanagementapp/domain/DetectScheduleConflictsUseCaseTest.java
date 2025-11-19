package com.example.leedstrinity.hospimanagementapp.domain;


import android.content.Context;

import com.example.leedstrinity.hospimanagementapp.data.dao.AppointmentDao;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class DetectScheduleConflictsUseCaseTest {

    @Test
    public void testHasConflict_returnsTrueWhenOverlapsExist() {
        AppointmentRepository repo = mock(AppointmentRepository.class);
        Context mockContext = mock(Context.class);

        // Simulate overlapping appointments
        List<Appointment> overlaps = Arrays.asList(new Appointment());
        when(repo.detectConflicts(1L, 1000L, 2000L)).thenReturn(overlaps);

        DetectScheduleConflictsUseCase useCase = new DetectScheduleConflictsUseCase(mockContext) {
            {
                this.repo = repo; // override with mock
            }
        };

        boolean result = useCase.hasConflict(1L, 1000L, 2000L);
        assertTrue(result);
        verify(repo).detectConflicts(1L, 1000L, 2000L);
    }
}

