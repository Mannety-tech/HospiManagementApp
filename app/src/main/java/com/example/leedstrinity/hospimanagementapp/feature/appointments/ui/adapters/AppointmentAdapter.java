package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    private List<Appointment> appointments = new ArrayList<>();
    private final OnItemClickListener listener;

    // Constructor with listener
    public AppointmentAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    // No-arg constructor (listener optional)
    public AppointmentAdapter() {
        this.listener = null;
    }

    // Allow updating the list dynamically
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments != null ? appointments : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appt = appointments.get(position);
        holder.bind(appt, listener);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvClinic;
        private final TextView tvSpecialist;
        private final TextView tvStatus;
        private final TextView tvReason;

        AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvClinic = itemView.findViewById(R.id.tvClinic);
            tvSpecialist = itemView.findViewById(R.id.tvSpecialist);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvReason = itemView.findViewById(R.id.tvReason);
        }

        public void bind(Appointment appt, OnItemClickListener listener) {
            String formattedTime = DateFormat.getTimeInstance(DateFormat.SHORT)
                    .format(new Date(appt.getStartTimeMillis()));

            tvDate.setText(appt.getDate() + " at " + formattedTime);
            tvClinic.setText("Clinic: " + appt.getClinicLocation());
            tvSpecialist.setText("Specialist: " + appt.getSpecialistName());
            tvStatus.setText("Status: " + appt.getStatus());
            tvReason.setText("Reason: " + appt.getReason());

            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(appt));
            } else {
                itemView.setOnClickListener(null);
            }
        }
    }
}





