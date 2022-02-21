package com.example.beyourownbartender;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.OffsetDateTime;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    public static final String BASE_URL = "http://api.impostor.services/";
    private static Retrofit retrofit;

    public static Retrofit getInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class,
                    (JsonDeserializer<OffsetDateTime>)(json, type, context)
                            -> OffsetDateTime.parse(json.getAsString())).create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
