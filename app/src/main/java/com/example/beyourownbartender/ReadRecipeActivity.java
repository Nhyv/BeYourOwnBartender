package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;

public class ReadRecipeActivity extends AppCompatActivity {
    Recipe recipe;
    TextView readTitre, readAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_recipe);

        Intent intent = getIntent();
        readTitre = findViewById(R.id.readTitre);
        readAuthor = findViewById(R.id.readAuthor);

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
                readTitre.setText(name);
                if (authorId == 7) {
                    readAuthor.setText("BeYourOwnBartender");
                }
                else {
                }
            }

            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {

            }
        });
    }
}