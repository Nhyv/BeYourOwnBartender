package com.example.beyourownbartender.serverInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.beyourownbartender.Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class serverConn {

    public HttpURLConnection createConn(String target) throws IOException {
        String baseUrl = "http://api.impostor.services/api";
        URL url = new URL(baseUrl + target);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }

    public /*List<Recipe>*/ void GetAllRecipes() throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        HttpURLConnection conn = createConn("/recipes");
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        System.out.println("Response Code : " + responseCode);
        System.out.println("Response Body : ");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String result = null;
        result = response.toString();
        System.out.println(result);
    }
}
