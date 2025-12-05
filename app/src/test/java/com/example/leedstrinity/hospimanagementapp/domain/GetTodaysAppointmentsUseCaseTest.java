import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.data.repo.AppointmentRepository;
import com.example.leedstrinity.hospimanagementapp.domain.GetTodaysAppointmentsUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GetTodaysAppointmentsUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppointmentRepository repo;
    private GetTodaysAppointmentsUseCase useCase;

    @Before
    public void setUp() {
        repo = mock(AppointmentRepository.class);
        useCase = new GetTodaysAppointmentsUseCase(repo);
    }

    @Test
    public void testExecuteReturnsAppointments() throws Exception {
        List<Appointment> appointments = Arrays.asList(new Appointment(), new Appointment());
        MutableLiveData<List<Appointment>> liveData = new MutableLiveData<>(appointments);

        when(repo.getTodaysAppointments("Clinic A", 0L, Long.MAX_VALUE)).thenReturn(liveData);

        LiveData<List<Appointment>> result = useCase.execute("Clinic A");

        assertNotNull(result);
        assertEquals(2, result.getValue().size());
    }

    @Test
    public void testExecuteReturnsEmptyListWhenNoAppointments() throws Exception {
        MutableLiveData<List<Appointment>> liveData = new MutableLiveData<>(Collections.emptyList());

        when(repo.getTodaysAppointments("Clinic A", 0L, Long.MAX_VALUE)).thenReturn(liveData);

        LiveData<List<Appointment>> result = useCase.execute("Clinic A");

        assertNotNull(result);
        assertTrue(result.getValue().isEmpty());
    }
}









