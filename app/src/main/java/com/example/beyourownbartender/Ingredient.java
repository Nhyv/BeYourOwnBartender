package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ingredient {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("brand")
    String brand;

    List<Ingredient> allIngredients;

    public Ingredient(int id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public Ingredient(int id, String name, String brand, List<Ingredient> allIngredients) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.allIngredients = allIngredients;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<Ingredient> getAllIngredients() {
        return allIngredients;
    }

    public void setAllIngredients(List<Ingredient> allIngredients) {
        this.allIngredients = allIngredients;
    }
}

