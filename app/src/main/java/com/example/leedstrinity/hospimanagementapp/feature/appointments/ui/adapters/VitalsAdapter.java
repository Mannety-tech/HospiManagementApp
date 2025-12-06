package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VitalsAdapter extends RecyclerView.Adapter<VitalsAdapter.VitalsViewHolder> {

    private final List<Vitals> vitalsList;
    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());

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

        // Format date
        String recordedAt = vitals.getRecordedAt() != null
                ? dateFormat.format(vitals.getRecordedAt())
                : "Unknown";

        holder.tvRecordedAt.setText("Recorded: " + recordedAt);
        holder.tvHeartRate.setText("Heart Rate: " + vitals.getHeartRate() + " bpm");
        holder.tvBloodPressure.setText("Blood Pressure: "
                + vitals.getSystolicBP() + "/" + vitals.getDiastolicBP() + " mmHg");
        holder.tvTemperature.setText("Temperature: " + vitals.getTemperature() + " °C");
        holder.tvRespRate.setText("Respiratory Rate: " + vitals.getRespiratoryRate() + " breaths/min");

        // --- Highlight the latest vitals entry (first item) ---
        if (position == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFF9C4")); // light yellow
            holder.tvRecordedAt.setTextColor(Color.parseColor("#D32F2F")); // red for emphasis
            holder.tvRecordedAt.setText(holder.tvRecordedAt.getText() + " (Latest)");
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.tvRecordedAt.setTextColor(Color.DKGRAY);
        }
    }

    @Override
    public int getItemCount() {
        return vitalsList != null ? vitalsList.size() : 0;
    }

    // Update method for LiveData observer
    public void updateVitals(List<Vitals> newVitals) {
        vitalsList.clear();
        vitalsList.addAll(newVitals);
        notifyDataSetChanged();
    }

    static class VitalsViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecordedAt, tvHeartRate, tvBloodPressure, tvTemperature, tvRespRate;

        VitalsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecordedAt = itemView.findViewById(R.id.tvRecordedAt);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            tvBloodPressure = itemView.findViewById(R.id.tvBloodPressure);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvRespRate = itemView.findViewById(R.id.tvRespRate);
        }
    }
}




