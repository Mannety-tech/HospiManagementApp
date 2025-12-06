package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Patient;
import com.example.leedstrinity.hospimanagementapp.PatientVitalsActivity;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private final List<Patient> patientList;

    public PatientAdapter(List<Patient> patientList) {
        // Defensive copy so we can safely clear/update later
        this.patientList = patientList != null ? new ArrayList<>(patientList) : new ArrayList<>();
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patientList.get(position);

        holder.tvPatientName.setText(patient.getName());
        holder.tvPatientId.setText("ID: " + patient.getId());

        Context context = holder.itemView.getContext();

        // --- Launch PatientVitalsActivity ---
        holder.btnVitalsPage.setOnClickListener(v -> {
            Intent intent = new Intent(context, PatientVitalsActivity.class);
            intent.putExtra("patientId", patient.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return patientList != null ? patientList.size() : 0;
    }

    // 🔥 New method to update patients list
    public void updatePatients(List<Patient> newPatients) {
        patientList.clear();
        if (newPatients != null) {
            patientList.addAll(newPatients);
        }
        notifyDataSetChanged();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvPatientId;
        Button btnVitalsPage;

        PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvPatientId = itemView.findViewById(R.id.tvPatientId);
            btnVitalsPage = itemView.findViewById(R.id.btnVitalsPage);
        }
    }
}





