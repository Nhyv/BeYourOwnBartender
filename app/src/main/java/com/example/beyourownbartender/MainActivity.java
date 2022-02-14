package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapterList adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvRecipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterList = new MainAdapterList();
        recyclerView.setAdapter(adapterList);

    }
}