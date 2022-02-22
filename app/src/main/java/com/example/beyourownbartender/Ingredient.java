package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("name")
    String name;

    @SerializedName("id")
    int id;

    @SerializedName("brand")
    String brand;
}
