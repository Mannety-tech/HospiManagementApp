package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters.AppointmentAdapter;

public class AppointmentListFragment extends Fragment {

    private Spinner spClinic;
    private ProgressBar progress;
    private RecyclerView rvAppointments;
    private AppointmentAdapter adapter;
    private AppointmentListViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_list, container, false);

        spClinic = view.findViewById(R.id.spClinic);
        progress = view.findViewById(R.id.progress);
        rvAppointments = view.findViewById(R.id.rvAppointments);
        rvAppointments.setLayoutManager(new LinearLayoutManager(requireContext()));

        //  Initialize adapter with click listener only
        adapter = new AppointmentAdapter(appointment -> {
            BookingFragment bookingFragment = BookingFragment.newInstance(appointment);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.appointmentContainer, bookingFragment)
                    .addToBackStack(null)
                    .commit();
        });
        rvAppointments.setAdapter(adapter);

        // ViewModel
        viewModel = new ViewModelProvider(this).get(AppointmentListViewModel.class);

        // Populate clinic spinner
        ArrayAdapter<CharSequence> clinicAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.clinic_locations,
                android.R.layout.simple_spinner_item
        );
        clinicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spClinic.setAdapter(clinicAdapter);

        // Observe appointments
        viewModel.getAllAppointments().observe(getViewLifecycleOwner(), appointments -> {
            progress.setVisibility(View.GONE);
            adapter.setAppointments(appointments);
        });

        return view;
    }
}





