package com.example.beyourownbartender;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerInterface {
    @GET("/recipes")
    Call<List<Recipe>> getRecipes();

    @POST("/auth/login")
    Call<LoggedInUser> getLogin(@Body Login login);

    @POST("/auth/register")
    Call<Void> addUser(@Body Registration registration);

    @GET("/recipes/{id}")
    Call<Recipe> getRecipeById(@Path("id") int id);

    @GET("/ingredients")
    Call<List<Ingredient>> getIngredients();

    @GET("/ingredients/recipe_{id}")
    Call<List<Ingredient>> getIngredientsByRecipeId(@Path("id") int id);

    @POST("/comments/add")
    Call<Void> addComment(@Body Comment comment);

    @GET("/comments/recipe_{id}")
    Call<List<Comment>> getCommentsByRecipeId(@Path("id") int id);
}
