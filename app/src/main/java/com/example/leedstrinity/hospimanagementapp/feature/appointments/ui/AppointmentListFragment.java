package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;
import com.example.leedstrinity.hospimanagementapp.domain.GetTodaysAppointmentsUseCase;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.AppointmentAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppointmentListFragment extends Fragment {

    private Spinner spClinic;
    private ProgressBar progress;
    private RecyclerView rvAppointments;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_appointment_list, container, false);

        // Initialize views
        spClinic = view.findViewById(R.id.spClinic);
        progress = view.findViewById(R.id.progress);
        rvAppointments = view.findViewById(R.id.rvAppointments);
        rvAppointments.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Setup clinic spinner
        ArrayAdapter<String> clinicAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"All Clinics", "Surgery A", "Surgery B"}
        );
        spClinic.setAdapter(clinicAdapter);


        // Initial load
        loadAppointments();

        return view;
    }

    private void loadAppointments() {
        progress.setVisibility(View.VISIBLE);

        String selectedClinic = spClinic.getSelectedItemPosition() == 0
                ? null
                : spClinic.getSelectedItem().toString();

        executor.execute(() -> {
            try {
                List<Appointment> appointments =
                        new GetTodaysAppointmentsUseCase(requireContext()).execute(selectedClinic);

                requireActivity().runOnUiThread(() -> {
                    progress.setVisibility(View.GONE);
                    rvAppointments.setAdapter(new AppointmentAdapter(appointments, appointment -> {
                        BookingFragment bookingFragment = BookingFragment.newInstance(appointment);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.appointmentContainer, bookingFragment)
                                .addToBackStack(null)
                                .commit();
                    }));
                });

            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(),
                            "Failed to load appointments. Please retry.",
                            Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        executor.shutdown(); // clean up thread executor
    }
}



