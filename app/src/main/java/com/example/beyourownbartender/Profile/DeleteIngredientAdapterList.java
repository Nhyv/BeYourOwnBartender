package com.example.beyourownbartender.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beyourownbartender.Creation.IngredientDisplay;
import com.example.beyourownbartender.R;
import com.example.beyourownbartender.RetrofitInstance;
import com.example.beyourownbartender.ServerInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteIngredientAdapterList extends RecyclerView.Adapter<DeleteIngredientAdapterList.AdapterListViewHolder>{

    List<IngredientDisplay> ingredientList;
    Context context;

    public DeleteIngredientAdapterList(List<IngredientDisplay> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    public DeleteIngredientAdapterList(List<IngredientDisplay> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public AdapterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.delete_layout, parent, false);
        return new AdapterListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteIngredientAdapterList.AdapterListViewHolder holder, int position) {

        // Sets the card title text
        String titleCardIngredientString = "Ingredient:";
        holder.tvTitleCardIngredient.setText(titleCardIngredientString);

        List<IngredientDisplay> allIngredients = ingredientList.get(position).getAllIngredients();
        List<String> dropDownText = new ArrayList<>();
        for(int i = 0; i < allIngredients.size(); i++){
            dropDownText.add(allIngredients.get(i).getName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, dropDownText);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerAllIngredients.setAdapter(dataAdapter);

        // Sets the selected index on refresh (is -1 if name is undef)
        int index = dataAdapter.getPosition(ingredientList.get(position).getName());
        holder.spinnerAllIngredients.setSelection(index);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    // Adds an empty ingredient to the RV
    public void addIngredient(IngredientDisplay item){
        ingredientList.add(item);
        notifyItemInserted(ingredientList.size()-1);
    }

    // Removes an ingredient at index
    public void deleteIngredient(int index){
        ingredientList.remove(index);
        notifyItemRemoved(index);
    }

    // Notifies a change to each position
    public void refreshAll(){
        notifyDataSetChanged();
    }

    public class AdapterListViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitleCardIngredient;
        public Spinner spinnerAllIngredients;

        public AdapterListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitleCardIngredient = itemView.findViewById(R.id.tvTitleCardIngredient);
            spinnerAllIngredients = itemView.findViewById(R.id.spinnerAllIngredients);

            spinnerAllIngredients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String selectedItem = adapterView.getItemAtPosition(i).toString();
                    ingredientList.get(getLayoutPosition()).setName(selectedItem);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }
}
