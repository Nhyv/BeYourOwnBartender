package com.example.beyourownbartender;

import android.app.Activity;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class StepAdapterList extends RecyclerView.Adapter<StepAdapterList.AdapterListViewHolder> {

    List<String> stepList;
    Context context;
    private RecyclerView rvSteps;


    public StepAdapterList(List<String> stepList, Context context, RecyclerView rvSteps) {
        this.stepList = stepList;
        this.context = context;
        this.rvSteps = rvSteps;
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

        // Sets the card title text
        String titleCardStepString = "Etape No: "+(position+1);
        holder.tvTitleStep.setText(titleCardStepString);

        // Sets innerText
        holder.mtbStepInfo.setText(stepList.get(position));
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

    public void deleteStep(int index){
        stepList.remove(index);
        notifyItemRemoved(index);
    }

    public void startUpdate(int pos, String oldStep){
        Intent intent =  new Intent(context, UpdateStep.class);
        intent.putExtra("pos", pos);
        intent.putExtra("oldStep", oldStep);
        ((Activity)context).startActivityForResult(intent, 1);
    }

    public void concludeUpdate(int pos, String newStep){
        stepList.set(pos, newStep);
        notifyItemChanged(pos);
    }

    public void refreshAll(){
        notifyDataSetChanged();
    }


    public class AdapterListViewHolder extends RecyclerView.ViewHolder {
        public Button btRemoveStep;
        public Button btUpdateStep;
        public TextView tvTitleStep;
        public TextView mtbStepInfo;

        public AdapterListViewHolder(@NonNull View itemView) {
            super(itemView);

            btRemoveStep = itemView.findViewById(R.id.btRemoveStep);
            btUpdateStep = itemView.findViewById(R.id.btUpdateStep);
            tvTitleStep = itemView.findViewById(R.id.tvTitleStep);
            mtbStepInfo = itemView.findViewById(R.id.mtbStepInfo);


            btRemoveStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteStep(getLayoutPosition());
                    refreshAll();
                }
            });

            btUpdateStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   startUpdate(getLayoutPosition(), stepList.get(getLayoutPosition()));
                }
            });
        }
    }
}
