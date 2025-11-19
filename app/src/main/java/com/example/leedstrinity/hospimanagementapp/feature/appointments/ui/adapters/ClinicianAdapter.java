
package com.example.leedstrinity.hospimanagementapp.feature.appointments.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.leedstrinity.hospimanagementapp.data.entities.Clinician;

import java.util.ArrayList;
import java.util.List;

public class ClinicianAdapter extends RecyclerView.Adapter<ClinicianAdapter.ViewHolder> {
    private final List<Clinician> clinicianList = new ArrayList<>();

    public <E> ClinicianAdapter(ArrayList<E> es) {
    }

    public ClinicianAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Clinician clinician = clinicianList.get(position);
        holder.textView.setText(clinician.fullName + " (" + clinician.specialty + ")");
    }

    @Override
    public int getItemCount() {
        return clinicianList.size();
    }

    // Update list from DB
    public void setClinicians(List<Clinician> clinicians) {
        clinicianList.clear();
        clinicianList.addAll(clinicians);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}



