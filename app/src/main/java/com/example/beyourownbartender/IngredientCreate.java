package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class IngredientCreate {
    @SerializedName("name")
    String name;

    @SerializedName("brand")
    String brand;

    @SerializedName("imageData")
    String imageData; // That's where you put the base64 of the inputStream.
}
