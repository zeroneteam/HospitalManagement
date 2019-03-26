package com.zerone.hospitalmanagement;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zerone.hospitalmanagement.Model.PatientModel;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    private List<PatientModel> patientModelList;

    public PatientAdapter(List<PatientModel> patientModelList) {
        this.patientModelList = patientModelList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_row,viewGroup,false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder patientViewHolder, int i) {
        final PatientModel patientModel = patientModelList.get(i);

        patientViewHolder.patientNameTV.setText(patientModel.getName());
        patientViewHolder.patientGenderTV.setText(patientModel.getGender());
        patientViewHolder.patientAgeTV.setText(patientModel.getAge());
    }

    @Override
    public int getItemCount() {
        return patientModelList.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder{
        TextView patientNameTV, patientGenderTV, patientAgeTV;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            patientNameTV = itemView.findViewById(R.id.patientNameTV);
            patientGenderTV = itemView.findViewById(R.id.patientGenderTV);
            patientAgeTV = itemView.findViewById(R.id.patientAgeTV);
        }
    }
}
