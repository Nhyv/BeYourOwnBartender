package com.example.beyourownbartender.Creation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientDisplay {
    @SerializedName("name")
    String name;

    @SerializedName("id")
    int id;

    @SerializedName("imageUrl")
    String imageUrl;

    List<IngredientDisplay> allIngredients;


    public IngredientDisplay(int id, String name) {
        this.name = name;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public IngredientDisplay(int id, String name, List<IngredientDisplay> allIngredients) {
        this.id = id;
        this.name = name;
        this.allIngredients = allIngredients;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<IngredientDisplay> getAllIngredients() {
        return allIngredients;
    }

    public void setAllIngredients(List<IngredientDisplay> allIngredients) {
        this.allIngredients = allIngredients;
    }
}
