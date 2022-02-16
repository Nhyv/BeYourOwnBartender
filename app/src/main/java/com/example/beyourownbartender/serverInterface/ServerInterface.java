package com.example.beyourownbartender.serverInterface;

import com.example.beyourownbartender.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServerInterface {
    @GET("/recipes")
    Call<List<Recipe>> getAllRecipes();
}
