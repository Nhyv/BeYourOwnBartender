package com.example.beyourownbartender;

import com.example.beyourownbartender.Comments.CommentCreate;
import com.example.beyourownbartender.Comments.CommentDisplay;
import com.example.beyourownbartender.Creation.IngredientCreate;
import com.example.beyourownbartender.Creation.IngredientDisplay;
import com.example.beyourownbartender.Profile.LikeDisplay;
import com.example.beyourownbartender.Startup.LoggedInUser;
import com.example.beyourownbartender.Startup.Login;
import com.example.beyourownbartender.Startup.Registration;
import com.example.beyourownbartender.Startup.UserDisplay;
import com.example.beyourownbartender.Update.RecipePatchNoImage;
import com.example.beyourownbartender.Update.RecipePatchWithImage;
import com.example.beyourownbartender.Welcome.RecipeCreate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
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

    @GET("/api/users/{username}")
    Call<UserDisplay> getUserByUsername(@Path("username") String username);

    @GET("/api/users/{id}/liked")
    Call<List<Integer>> getUserLiked(@Path("id") int id);

    @POST("/api/recipes/{id}/like/{userId}")
    Call<LikeDisplay> toggleLikeRecipe(@Path("id") int id, @Path("userId") int userId);

    @POST("/api/recipes/add")
    Call<RecipeDisplay> addRecipe(@Body RecipeCreate recipe);

    @POST("/api/recipes/{id}/ingredients/add")
    Call<List<IngredientDisplay>> addIngredientToRecipe(@Path("id") int id, @Body List<IngredientDisplay> ingredientDisplayToLink);

    @GET("/api/recipes/user/{id}")
    Call<ArrayList<RecipeDisplay>> getUserRecipes(@Path("id") int id);

    @GET("/api/recipes/user/{id}/liked")
    Call<ArrayList<RecipeDisplay>> getUserLikedRecipes(@Path("id") int id);

    @DELETE("/api/recipes/delete/{id}")
    Call<Void> deleteRecipeById(@Path("id") int id);

    @DELETE("/api/comments/delete/{id}")
    Call<Void> deleteCommentById(@Path("id") int id);

    @DELETE("/api/ingredients/delete/{id}")
    Call<Void> deleteIngredientById(@Path("id") int id);

    @PATCH("/api/recipes/{id}/modify")
    Call<RecipeDisplay> patchRecipeById(@Path("id") int id, @Body RecipePatchNoImage patchRecipe);

    @PATCH("/api/recipes/{id}/modify")
    Call<RecipeDisplay> patchRecipeById(@Path("id") int id, @Body RecipePatchWithImage recipeDisplay);
}
