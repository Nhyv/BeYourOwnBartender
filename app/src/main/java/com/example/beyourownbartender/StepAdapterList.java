package com.example.beyourownbartender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StepAdapterList extends RecyclerView.Adapter<StepAdapterList.AdapterListViewHolder> {

    List<String> stepList;
    Context context;

    public StepAdapterList(List<String> stepList, Context context) {
        this.stepList = stepList;
        this.context = context;
    }

    public StepAdapterList(List<String> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public AdapterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.add_step_to_recipe_layout, parent, false);
        return new AdapterListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    // Adds an empty String to the RV
    public void addStep(String item){
        stepList.add(item);
        notifyItemInserted(stepList.size()-1);
    }


    public class AdapterListViewHolder extends RecyclerView.ViewHolder {

        public AdapterListViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
