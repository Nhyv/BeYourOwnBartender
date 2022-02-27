package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class IngredientDisplay {
    @SerializedName("name")
    String name;

    @SerializedName("id")
    int id;

    @SerializedName("brand")
    String brand;

    @SerializedName("imageUrl")
    String imageUrl;

    public IngredientDisplay(String name, int id, String brand, String imageUrl) {
        this.name = name;
        this.id = id;
        this.brand = brand;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
