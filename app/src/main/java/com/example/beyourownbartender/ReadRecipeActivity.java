package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class ReadRecipeActivity extends AppCompatActivity {
    Recipe recipe;
    TextView readTitre, readAuthor, readTags, readIngredients, readSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_recipe);

        Intent intent = getIntent();
        readTitre = findViewById(R.id.readTitre);
        readAuthor = findViewById(R.id.readAuthor);
        readTags = findViewById(R.id.readTags);
        readIngredients = findViewById(R.id.readIngredients);
        readSteps = findViewById(R.id.readSteps);

        int id = 0;
        if (intent.hasExtra("recipeId")) {
            Bundle bundle = intent.getExtras();
            id = bundle.getInt("recipeId", 0);
        }
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<Recipe> call = server.getRecipeById(id);
        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                recipe = response.body();
                String name = recipe.getName();
                int authorId = recipe.getAuthorid();
                List<String> tags = recipe.getTags();
                List<String> steps = recipe.getSteps();
                String toShowTags = "";
                String toShowSteps = "";

                for (int i = 0; i < tags.size(); i++) {
                    if (tags.size() == 1)
                        toShowTags = tags.get(i);
                    else
                        toShowTags += tags.get(i) + ", ";
                }

                for (int i = 0; i < steps.size(); i++) {
                    if (steps.size() == 1)
                        toShowSteps = steps.get(i);
                    else
                        toShowSteps += steps.get(i) + System.lineSeparator();
                }

                readTags.setText(toShowTags);
                readSteps.setText(toShowSteps);
                readTitre.setText(name);
                if (authorId == 7) {
                    readAuthor.setText("BeYourOwnBartender");
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) { }
        });

        Call<List<Ingredient>> callIngredients = server.getIngredientsByRecipeId(id);
        callIngredients.enqueue(new Callback<List<Ingredient>>() {
            @Override
            public void onResponse(Call<List<Ingredient>> call, Response<List<Ingredient>> response) {
                List<Ingredient> ingredients = response.body();
                String toDisplay = "";

                for (int i = 0; i < ingredients.size(); i++) {
                    toDisplay += ingredients.get(i).name + System.lineSeparator();
                }

                readIngredients.setText(toDisplay);
            }

            @Override
            public void onFailure(Call<List<Ingredient>> call, Throwable t) { }
        });
    }
}