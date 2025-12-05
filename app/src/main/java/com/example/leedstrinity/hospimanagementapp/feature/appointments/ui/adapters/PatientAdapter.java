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
import com.example.leedstrinity.hospimanagementapp.VitalsActivity;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {

    private final List<Patient> patientList;

    public PatientAdapter(List<Patient> patientList) {
        this.patientList = patientList;
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

        // --- View Vitals button click ---
        holder.btnViewVitals.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, VitalsActivity.class);
            intent.putExtra("patientId", patient.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return patientList != null ? patientList.size() : 0;
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvPatientId;
        Button btnViewVitals;

        PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tvPatientName);
            tvPatientId = itemView.findViewById(R.id.tvPatientId);
            btnViewVitals = itemView.findViewById(R.id.btnViewVitals);
        }
    }
}


