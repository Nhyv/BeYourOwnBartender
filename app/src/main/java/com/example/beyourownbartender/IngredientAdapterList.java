package com.example.beyourownbartender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.List;

public class IngredientAdapterList extends RecyclerView.Adapter<IngredientAdapterList.AdapterListViewHolder>{

    List<Ingredient> ingredientList;
    Context context;

    public IngredientAdapterList(List<Ingredient> ingredientList, Context context) {
        this.ingredientList = ingredientList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.add_ingredient_to_recipe_layout, parent, false);
        return new AdapterListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapterList.AdapterListViewHolder holder, int position) {

        // Sets the card title text
        String titleCardIngredientString = "Ingredient No: "+(position+1);
        holder.tvTitleCardIngredient.setText(titleCardIngredientString);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    // Adds an empty ingredient to the RV
    public void addIngredient(Ingredient item){
        ingredientList.add(item);
        notifyItemInserted(ingredientList.size()-1);
    }

    public class AdapterListViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitleCardIngredient;

        public AdapterListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitleCardIngredient = itemView.findViewById(R.id.tvTitleCardIngredient);
        }
    }
}
