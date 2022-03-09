package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class IngredientCreate {
    @SerializedName("name")
    String name;

    public IngredientCreate(String name) {
        this.name = name;
    }
}
