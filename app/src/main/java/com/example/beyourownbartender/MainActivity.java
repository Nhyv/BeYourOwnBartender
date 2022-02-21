package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapterList adapterList;
    List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvRecipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<Recipe>> call = server.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes = response.body();

                adapterList = new MainAdapterList(recipes);
                recyclerView.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}