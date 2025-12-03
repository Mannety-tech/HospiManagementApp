package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Appointment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    private final List<Appointment> appointments;
    private final OnItemClickListener listener;

    public AppointmentAdapter(List<Appointment> appointments, OnItemClickListener listener) {
        this.appointments = appointments;
        this.listener = listener;
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvPatientName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatient);
        }

        public void bind(Appointment appointment, OnItemClickListener listener) {
            tvPatientName.setText(appointment.getPatientName());


            itemView.setOnClickListener(v -> listener.onItemClick(appointment));
        }
    }
}


