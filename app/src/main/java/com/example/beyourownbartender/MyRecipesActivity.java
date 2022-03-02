package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRecipesActivity extends AppCompatActivity {
    private RecyclerView mrRecipe;
    private MainAdapterList adapterList;
    List<RecipeDisplay> recipes;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);
        getSupportActionBar().setTitle("Mes recettes");

        pref = getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
        int userId = pref.getInt("userId", 0);

        mrRecipe = findViewById(R.id.mrRecipe);
        mrRecipe.setLayoutManager(new LinearLayoutManager(this));

        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<RecipeDisplay>> call = server.getUserRecipes(userId);

        call.enqueue(new Callback<List<RecipeDisplay>>() {
            @Override
            public void onResponse(Call<List<RecipeDisplay>> call, Response<List<RecipeDisplay>> response) {
                recipes = response.body();
                adapterList = new MainAdapterList(recipes, getMyRecipesActivity());
                mrRecipe.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<RecipeDisplay>> call, Throwable t) {

            }
        });
    }

    public MyRecipesActivity getMyRecipesActivity() {
        return this;
    }

    public void startReadRecipeActivity(RecipeDisplay recipe) {
        Intent intent = new Intent(this, ReadRecipeActivity.class);
        intent.putExtra("recipeId", recipe.getId());
        intent.putExtra("authorId", recipe.getAuthorid());
        startActivity(intent);
    }
}