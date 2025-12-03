package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VitalsAdapter extends RecyclerView.Adapter<VitalsAdapter.VitalsViewHolder> {

    private List<Vitals> vitalsList = new ArrayList<>();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public void setVitals(List<Vitals> vitals) {
        this.vitalsList = vitals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VitalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vitals, parent, false);
        return new VitalsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VitalsViewHolder holder, int position) {
        Vitals vitals = vitalsList.get(position);
        holder.tvHeartRate.setText("Heart Rate: " + vitals.getHeartRate() + " bpm");
        holder.tvBloodPressure.setText("Blood Pressure: " + vitals.getSystolicBP() + "/" + vitals.getDiastolicBP() + " mmHg");
        holder.tvTemperature.setText("Temperature: " + vitals.getTemperature() + " °C");
        holder.tvRespRate.setText("Respiratory Rate: " + vitals.getRespiratoryRate() + " breaths/min");
        holder.tvRecordedAt.setText("Recorded at: " + dateFormat.format(vitals.getRecordedAt()));
    }

    @Override
    public int getItemCount() {
        return vitalsList.size();
    }

    static class VitalsViewHolder extends RecyclerView.ViewHolder {
        TextView tvHeartRate, tvBloodPressure, tvTemperature, tvRespRate, tvRecordedAt;

        VitalsViewHolder(View itemView) {
            super(itemView);
            tvHeartRate = itemView.findViewById(R.id.tvHeartRate);
            tvBloodPressure = itemView.findViewById(R.id.tvBloodPressure);
            tvTemperature = itemView.findViewById(R.id.tvTemperature);
            tvRespRate = itemView.findViewById(R.id.tvRespiratoryRate);
            tvRecordedAt = itemView.findViewById(R.id.tvRecordedAt);
        }
    }
}

