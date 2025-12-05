package com.example.leedstrinity.hospimanagementapp.domain;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class DetectScheduleConflictsUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppointmentRepository repo;
    private DetectScheduleConflictsUseCase useCase;

    @Before
    public void setUp() {
        repo = mock(AppointmentRepository.class);
        Context mockContext = mock(Context.class);

        useCase = new DetectScheduleConflictsUseCase(mockContext) {
            {
                this.repo = repo; // override with mock
            }
        };
    }

    @Test
    public void testHasConflict_returnsTrueWhenOverlapsExist() {
        List<Appointment> overlaps = Arrays.asList(new Appointment());
        MutableLiveData<List<Appointment>> liveData = new MutableLiveData<>(overlaps);

        when(repo.detectConflicts("Dr. Smith", 1000L, 2000L)).thenReturn(liveData);

        boolean result = useCase.hasConflict("Dr. Smith", 1000L, 2000L);

        assertTrue(result);
    }

    @Test
    public void testHasConflict_returnsFalseWhenNoOverlapsExist() {
        MutableLiveData<List<Appointment>> liveData = new MutableLiveData<>(Collections.emptyList());

        when(repo.detectConflicts("Dr. Smith", 1000L, 2000L)).thenReturn(liveData);

        boolean result = useCase.hasConflict("Dr. Smith", 1000L, 2000L);

        assertFalse(result);
    }
}







