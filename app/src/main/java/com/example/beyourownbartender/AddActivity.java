package com.example.beyourownbartender;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;
    private IngredientAdapterList ingredientAdapterList;
    private StepAdapterList stepAdapterList;
    private List<IngredientDisplay> ingredientList;
    private List<IngredientDisplay> allIngredientList;
    private List<String> stepList;
    private Button buttonAddIngredient;
    private Button buttonAddStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Creates the empty ingredient list and sets the recyclerview/adapterlist values for ingredients
        ingredientList = createListIngredients();
        rvIngredients = findViewById(R.id.rvIngredients);
        rvIngredients.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapterList = new IngredientAdapterList(ingredientList, this);
        rvIngredients.setAdapter(ingredientAdapterList);

        /// Creates the empty String list and sets the recyclerview/adapterlist values for steps
        stepList = createListSteps();
        rvSteps = findViewById(R.id.rvSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));
        stepAdapterList = new StepAdapterList(stepList, this, rvSteps);
        rvSteps.setAdapter(stepAdapterList);


        // This is used to pull the ingredients data from the DB
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<List<IngredientDisplay>> call = server.getIngredients();

        call.enqueue(new Callback<List<IngredientDisplay>>() {
            @Override
            public void onResponse(Call<List<IngredientDisplay>> call, Response<List<IngredientDisplay>> response) {
                allIngredientList = response.body();
                ingredientAdapterList.addIngredient(new IngredientDisplay(0, "", null));
            }

            @Override
            public void onFailure(Call<List<IngredientDisplay>> call, Throwable t) {

            }
        });

        rvSteps = findViewById(R.id.rvSteps);
        rvSteps.setLayoutManager(new LinearLayoutManager(this));

        // Creates a onClickListener for the addIngredient button
        buttonAddIngredient = findViewById(R.id.btAddIngredients);
        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adds an empty ingredient containing a list of all ingredients
                ingredientAdapterList.addIngredient(new IngredientDisplay(0, "", null));
            }
        });

        // Creates a onClickListener for the addStep button
        buttonAddStep = findViewById(R.id.btAddSteps);
        buttonAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adds an empty step
                stepAdapterList.addStep("");
            }
        });
    }

    // Creates the empty ingredient list
    private List<IngredientDisplay> createListIngredients(){
        List<IngredientDisplay> ingredientList = new ArrayList<>();
        return(ingredientList);
    }

    private List<String> createListSteps(){
        List<String> stepList = new ArrayList<>();
        stepList.add("");
        return(stepList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                stepAdapterList.concludeUpdate(Integer.parseInt(data.getStringExtra("pos")),data.getStringExtra("newStep"));
            }
        }
    }
}