package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;
import com.example.leedstrinity.hospimanagementapp.R;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VitalsAdapter extends RecyclerView.Adapter<VitalsAdapter.VitalsViewHolder> {

    private List<Vitals> vitalsList;

    public VitalsAdapter(List<Vitals> vitalsList) {
        this.vitalsList = vitalsList;
    }

    @NonNull
    @Override
    public VitalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vital, parent, false);
        return new VitalsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VitalsViewHolder holder, int position) {
        Vitals vitals = vitalsList.get(position);

        holder.tvHeartRate.setText("Heart Rate: " + vitals.getHeartRate() + " bpm");

        // ✅ Combine systolic + diastolic values
        holder.tvBloodPressure.setText("Blood Pressure: "
                + vitals.getSystolicBP() + "/" + vitals.getDiastolicBP() + " mmHg");

        holder.tvTemperature.setText("Temperature: " + vitals.getTemperature() + " °C");
        holder.tvRespiratoryRate.setText("Respiratory Rate: " + vitals.getRespiratoryRate() + " breaths/min");

        // Format timestamp nicely
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        holder.tvRecordedAt.setText("Recorded: " + sdf.format(vitals.getRecordedAt()));
    }

    @Override
    public int getItemCount() {
        return vitalsList != null ? vitalsList.size() : 0;
    }

    public void updateVitals(List<Vitals> newVitals) {
        this.vitalsList = newVitals;
        notifyDataSetChanged();
    }

    static class VitalsViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeartRate, tvBloodPressure, tvTemperature, tvRespiratoryRate, tvRecordedAt;

        VitalsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            tvBloodPressure = itemView.findViewById(R.id.tvBloodPressure);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvRespiratoryRate = itemView.findViewById(R.id.tvRespiratoryRate);
            tvRecordedAt = itemView.findViewById(R.id.tvRecordedAt);
        }
    }
}



