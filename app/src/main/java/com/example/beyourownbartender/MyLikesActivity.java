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

public class MyLikesActivity extends AppCompatActivity {
    private RecyclerView mlRecipe;
    private MainAdapterList adapterList;
    List<RecipeDisplay> recipes;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_likes);
        getSupportActionBar().setTitle("Mes ❤");

        pref = getSharedPreferences("BYOBPreferences", MODE_PRIVATE);
        int userId = pref.getInt("userId", 0);

        mlRecipe = findViewById(R.id.mlRecipe);
        mlRecipe.setLayoutManager(new LinearLayoutManager(this));

        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<RecipeDisplay>> call = server.getUserLikedRecipes(userId);

        call.enqueue(new Callback<List<RecipeDisplay>>() {
            @Override
            public void onResponse(Call<List<RecipeDisplay>> call, Response<List<RecipeDisplay>> response) {
                recipes = response.body();
                adapterList = new MainAdapterList(recipes, getMyLikesActivity());
                mlRecipe.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<RecipeDisplay>> call, Throwable t) {

            }
        });
    }

    public MyLikesActivity getMyLikesActivity() {
        return this;
    }

    public void startReadRecipeActivity(RecipeDisplay recipe) {
        Intent intent = new Intent(this, ReadRecipeActivity.class);
        intent.putExtra("recipeId", recipe.getId());
        intent.putExtra("authorId", recipe.getAuthorid());
        startActivity(intent);
    }
}
