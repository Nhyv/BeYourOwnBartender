package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;
    private IngredientAdapterList ingredientAdapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        rvIngredients = findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapterList = new IngredientAdapterList(createListIngredients(), this);
        rvIngredients.setAdapter(ingredientAdapterList);


        rvSteps = findViewById(R.id.rvSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Ingredient> createListIngredients(){
        List<Ingredient> ingredientList = new ArrayList<>();

        ingredientList.add(new Ingredient());

        return(ingredientList);
    }
}