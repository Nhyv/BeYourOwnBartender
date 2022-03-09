package com.example.beyourownbartender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MainAdapterList adapterList;
    Context context;
    ArrayList<RecipeDisplay> recipes;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Liste de recettes");
        context = this;
        recyclerView = findViewById(R.id.rvRecipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<ArrayList<RecipeDisplay>> call = server.getRecipes();

        call.enqueue(new Callback<ArrayList<RecipeDisplay>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeDisplay>> call, Response<ArrayList<RecipeDisplay>> response) {
                recipes = response.body();

                adapterList = new MainAdapterList(recipes, getMainActivity());
                recyclerView.setAdapter(adapterList);
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeDisplay>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // When the application loads it forces the adapterList to update the DOM if it exists
        if(adapterList != null){
            // It updates from the DB each time
            ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
            Call<List<RecipeDisplay>> call = server.getRecipes();

            call.enqueue(new Callback<List<RecipeDisplay>>() {
                @Override
                public void onResponse(Call<List<RecipeDisplay>> call, Response<List<RecipeDisplay>> response) {
                    recipes = response.body();
                    // On success updates the adapterList data
                    if(recipes != null){
                        adapterList.updateDisplayedData(recipes);
                    }
                }

                @Override
                public void onFailure(Call<List<RecipeDisplay>> call, Throwable t) {
                    // Failed need to display the issue
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_layout, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("newText1",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);
                adapterList.getFilter().filter(newText);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapterList = new MainAdapterList(recipes, getMainActivity());
                recyclerView.setAdapter(adapterList);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("idAdd")) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);

            startActivity(intent);
        }
        if (item.getTitle().equals("idProfile")) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

            startActivity(intent);
        }
        return true;
    }

    public MainActivity getMainActivity() {
        return this;
    }

    public void startReadRecipeActivity(RecipeDisplay recipe) {
        Intent intent = new Intent(this, ReadRecipeActivity.class);
        intent.putExtra("recipeId", recipe.getId());
        intent.putExtra("authorId", recipe.getAuthorid());
        startActivity(intent);
    }

    private void hideSystemBars() {
        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController == null) {
            return;
        }
        // Configure the behavior of the hidden system bars
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
}