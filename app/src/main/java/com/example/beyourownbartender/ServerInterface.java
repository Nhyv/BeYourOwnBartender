package com.example.beyourownbartender;

import com.example.beyourownbartender.data.model.LoggedInUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServerInterface {
    @GET("/recipes")
    Call<List<Recipe>> getRecipes();

    @POST("/auth/login")
    Call<LoggedInUser> getLogin(@Body Login login);
}
