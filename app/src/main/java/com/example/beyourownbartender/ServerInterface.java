package com.example.beyourownbartender;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerInterface {
    @GET("/recipes")
    Call<List<Recipe>> getRecipes();

    @GET("/ingredients")
    Call<List<Ingredient>> getIngredients();

    @POST("/auth/login")
    Call<LoggedInUser> getLogin(@Body Login login);
}
