package com.example.beyourownbartender;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServerInterface {
    @GET("/api/recipes")
    Call<ArrayList<RecipeDisplay>> getRecipes();

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

    @POST("/api/ingredients/add")
    Call<IngredientDisplay> addIngredient(@Body IngredientCreate ingredient);

    @GET("/api/comments/recipe_{id}")
    Call<List<CommentDisplay>> getCommentsByRecipeId(@Path("id") int id);

    @GET("/api/users/{id}")
    Call<UserDisplay> getUserById(@Path("id") int id);

    @GET("/api/users/{id}/liked")
    Call<List<Integer>> getUserLiked(@Path("id") int id);

    @POST("/api/recipes/{id}/like/{userId}")
    Call<LikeDisplay> toggleLikeRecipe(@Path("id") int id, @Path("userId") int userId);

    @GET("/api/recipes/user/{id}")
    Call<ArrayList<RecipeDisplay>> getUserRecipes(@Path("id") int id);

    @GET("/api/recipes/user/{id}/liked")
    Call<ArrayList<RecipeDisplay>> getUserLikedRecipes(@Path("id") int id);

    @GET("/api/logs")
    Call<List<LogDisplay>> getLogs();

    @DELETE("/api/recipes/delete/{id}")
    Call<Void> deleteRecipeById(@Path("id") int id);

    @DELETE("/api/comments/delete/{id}")
    Call<Void> deleteCommentById(@Path("id") int id);
}
