package com.example.beyourownbartender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapterList adapterList;
    Context context;
    List<RecipeDisplay> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        recyclerView = findViewById(R.id.rvRecipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<RecipeDisplay>> call = server.getRecipes();

        call.enqueue(new Callback<List<RecipeDisplay>>() {
            @Override
            public void onResponse(Call<List<RecipeDisplay>> call, Response<List<RecipeDisplay>> response) {
                recipes = response.body();

                adapterList = new MainAdapterList(recipes, getMainActivity());
                recyclerView.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<List<RecipeDisplay>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);

        startActivity(intent);
        finish();
        return true;
    }

    public MainActivity getMainActivity() {
        return this;
    }

    public void startReadRecipeActivity(RecipeDisplay recipe) {
        Intent intent = new Intent(this, ReadRecipeActivity.class);
        intent.putExtra("recipeId", recipe.getId());
        startActivity(intent);
    }
}