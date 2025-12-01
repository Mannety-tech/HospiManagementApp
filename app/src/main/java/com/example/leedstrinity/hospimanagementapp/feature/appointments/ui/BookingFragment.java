package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

public class BookingFragment extends Fragment {

    private static final String ARG_APPOINTMENT_ID = "appointment_id";

    public static BookingFragment newInstance(Appointment appointment) {
        BookingFragment fragment = new BookingFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_APPOINTMENT_ID, appointment.getId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            long appointmentId = getArguments().getLong(ARG_APPOINTMENT_ID);
            // TODO: Load appointment details using this ID
        }
    }

    // TODO: Inflate your layout in onCreateView if needed
}

