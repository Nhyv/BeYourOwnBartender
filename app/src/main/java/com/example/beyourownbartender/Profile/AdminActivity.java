package com.example.beyourownbartender.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.beyourownbartender.Creation.IngredientDisplay;
import com.example.beyourownbartender.R;
import com.example.beyourownbartender.RetrofitInstance;
import com.example.beyourownbartender.ServerInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private List<IngredientDisplay> ingredients;
    Button btAdminDelete;
    ServerInterface server;
    Spinner spinner;
    ArrayList<String> ingredientNames;
    Context context;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btAdminDelete = findViewById(R.id.btAdminDelete);
        spinner = findViewById(R.id.spinnerDelete);
        context = this;

        server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<IngredientDisplay>> call = server.getIngredients();

        call.enqueue(new Callback<List<IngredientDisplay>>() {
            @Override
            public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                ingredients = response.body();

                ingredientNames = (ArrayList<String>) ingredients.stream().map(x -> x.getName()).collect(Collectors.toList());
                adapter = new ArrayAdapter<String>(context, R.layout.spinner_layout, ingredientNames);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) {

            }
        });

        btAdminDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientDisplay ingredient = ingredients.get(spinner.getSelectedItemPosition());

                Call<Void> call = server.deleteIngredientById(ingredient.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        ingredients.remove(spinner.getSelectedItemPosition());
                        ingredientNames.remove(spinner.getSelectedItemPosition());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(context, "Ingrédient supprimé!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }
}