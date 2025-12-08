package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.R;
import com.example.leedstrinity.hospimanagementapp.data.entities.Vitals;

import java.util.ArrayList;
import java.util.List;

public class VitalsAdapter extends RecyclerView.Adapter<VitalsAdapter.VitalsViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Vitals vitals);
    }

    private List<Vitals> vitalsList = new ArrayList<>();
    private final OnItemClickListener listener;

    public VitalsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setVitals(List<Vitals> vitalsList) {
        this.vitalsList = vitalsList != null ? vitalsList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VitalsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vitals, parent, false);
        return new VitalsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VitalsViewHolder holder, int position) {
        holder.bind(vitalsList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return vitalsList.size();
    }

    static class VitalsViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvBP, tvHR, tvTemp, tvOxygen, tvRecorded;

        VitalsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBP = itemView.findViewById(R.id.tvBP);
            tvHR = itemView.findViewById(R.id.tvHR);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvOxygen = itemView.findViewById(R.id.tvOxygen);
            tvRecorded = itemView.findViewById(R.id.tvRecorded);
        }

        void bind(Vitals vitals, OnItemClickListener listener) {
            tvBP.setText("BP: " + safe(vitals.getBloodPressure()));
            tvHR.setText("HR: " + safe(vitals.getHeartRate()));
            tvTemp.setText("Temp: " + safe(vitals.getTemperature()));
            tvOxygen.setText("O2: " + safe(vitals.getOxygenLevel()));
            tvRecorded.setText("Recorded: " + safe(vitals.getFormattedRecordedAt()));

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(vitals);
            });
        }

        private String safe(String value) {
            return value != null ? value : "-";
        }
    }
}







