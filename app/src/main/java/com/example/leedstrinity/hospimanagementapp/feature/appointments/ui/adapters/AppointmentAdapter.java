package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.bind(appointment, listener);
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPatientName;
        private final TextView tvReason;
        private final TextView tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatient);
            tvReason = itemView.findViewById(R.id.tvReason);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(Appointment appointment, OnItemClickListener listener) {
            tvPatientName.setText(appointment.getPatientName());
            tvReason.setText(appointment.getReason());
            tvDate.setText(appointment.getDate());

            if (listener != null) {
                itemView.setOnClickListener(v -> listener.onItemClick(appointment));
            } else {
                itemView.setOnClickListener(null);
            }
        }
    }
}




