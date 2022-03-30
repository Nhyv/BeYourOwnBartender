package com.example.beyourownbartender.Update;

import com.example.beyourownbartender.Creation.IngredientDisplay;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePatchWithImage {
    @SerializedName("name")
    String name;

    @SerializedName("imageData")
    String imageData;

    @SerializedName("steps")
    List<String> steps;

    @SerializedName("tags")
    List<String> tags;

    @SerializedName("ingredients")
    List<IngredientDisplay> ingredients;

    public RecipePatchWithImage(String name, String imageData, List<String> tags, List<String> steps, List<IngredientDisplay> ingredients) {
        this.name = name;
        this.imageData = imageData;
        this.tags = tags;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public List<IngredientDisplay> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDisplay> ingredients) {
        this.ingredients = ingredients;
    }
}
