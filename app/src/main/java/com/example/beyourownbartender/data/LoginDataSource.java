package com.example.beyourownbartender.data;

import android.os.StrictMode;
import com.example.beyourownbartender.Login;
import com.example.beyourownbartender.RetrofitInstance;
import com.example.beyourownbartender.ServerInterface;
import com.example.beyourownbartender.data.model.LoggedInUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDataSource {
    LoggedInUser loggedInUser;
    volatile boolean isSuccessful;

    public Result<LoggedInUser> login(String username, String password) throws IOException {
        ServerInterface server = RetrofitInstance.getInstance().create(ServerInterface.class);
        Call<LoggedInUser> call = server.getLogin(new Login(username, password));

        call.enqueue(new Callback<LoggedInUser>() {
            @Override
            public void onResponse(Call<LoggedInUser> call, Response<LoggedInUser> response) {
                loggedInUser = response.body();
                isSuccessful = true;
            }

            @Override
            public void onFailure(Call<LoggedInUser> call, Throwable t) {
                isSuccessful = false;
            }
        });

        if (isSuccessful) {
            return new Result.Success<>(loggedInUser);
        }

        return null;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}