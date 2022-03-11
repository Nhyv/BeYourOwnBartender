package com.example.beyourownbartender.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.beyourownbartender.Creation.IngredientDisplay;
import com.example.beyourownbartender.R;
import com.example.beyourownbartender.RetrofitInstance;
import com.example.beyourownbartender.ServerInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity {
    private RecyclerView rvDeleteIngr;
    private List<IngredientDisplay> ingredients;
    private List<IngredientDisplay> allIngredients;
    private DeleteIngredientAdapterList adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ingredients = new ArrayList<>();
        adapterList = new DeleteIngredientAdapterList(ingredients, this);

        rvDeleteIngr = findViewById(R.id.rvDeleteIngr);
        rvDeleteIngr.setLayoutManager(new LinearLayoutManager(this));
        rvDeleteIngr.setAdapter(adapterList);

        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<IngredientDisplay>> call = server.getIngredients();

        call.enqueue(new Callback<List<IngredientDisplay>>() {
            @Override
            public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                allIngredients = response.body();
                adapterList.addIngredient(new IngredientDisplay(0, "", allIngredients));
            }

            @Override
            public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) {

            }
        });
    }
}