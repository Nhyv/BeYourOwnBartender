package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AddActivity extends AppCompatActivity {
    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        rvIngredients = findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));

        rvSteps = findViewById(R.id.rvSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
    }
}