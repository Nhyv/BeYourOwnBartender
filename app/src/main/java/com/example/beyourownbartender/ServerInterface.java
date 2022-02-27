package com.example.beyourownbartender;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServerInterface {
    @GET("/api/recipes")
    Call<List<RecipeDisplay>> getRecipes();

    @POST("/api/auth/login")
    Call<LoggedInUser> getLogin(@Body Login login);

    @POST("/api/auth/register")
    Call<Void> addUser(@Body Registration registration);

    @GET("/api/recipes/{id}")
    Call<RecipeDisplay> getRecipeById(@Path("id") int id);

    @GET("/api/ingredients")
    Call<List<IngredientDisplay>> getIngredients();

    @GET("/api/ingredients/recipe_{id}")
    Call<List<IngredientDisplay>> getIngredientsByRecipeId(@Path("id") int id);

    @POST("/api/comments/add")
    Call<CommentDisplay> addComment(@Body CommentCreate comment);

    @GET("/api/comments/recipe_{id}")
    Call<List<CommentDisplay>> getCommentsByRecipeId(@Path("id") int id);
}
