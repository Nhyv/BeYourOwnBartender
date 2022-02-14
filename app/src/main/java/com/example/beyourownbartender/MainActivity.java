package com.example.beyourownbartender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.example.beyourownbartender.serverInterface.serverConn;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonGetAllRecipes = findViewById(R.id.buttonGetAllRecipes);
        buttonGetAllRecipes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                serverConn serverConn = new serverConn();
                try {
                    serverConn.GetAllRecipes();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}