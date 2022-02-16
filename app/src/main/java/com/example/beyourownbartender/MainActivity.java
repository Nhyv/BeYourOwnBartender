package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.beyourownbartender.serverInterface.RetrofitInstance;
import com.example.beyourownbartender.serverInterface.ServerInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context context = null;
    public TextView tvTest;
    public Button buttonGetAllRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGetAllRecipes = findViewById(R.id.buttonGetAllRecipes);
        tvTest = findViewById(R.id.tvTest);
        buttonGetAllRecipes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
                Call<List<Recipe>> call = server.getAllRecipes();
                System.out.println("Test on click");
                call.enqueue(new Callback<List<Recipe>>()
                {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        //List<Recipe> allRecipes = response.body();
                        tvTest.setText("This is a test");
                        System.out.println("test on response");
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        System.out.println("test on failure");
                    }
                });

            }
        });

    }
}