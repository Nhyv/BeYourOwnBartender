package com.example.beyourownbartender.Update;

import com.example.beyourownbartender.Creation.IngredientDisplay;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePatchNoImage {
    @SerializedName("name")
    String name;

    @SerializedName("steps")
    List<String> steps;

    @SerializedName("tags")
    List<String> tags;

    @SerializedName("ingredients")
    List<IngredientDisplay> ingredients;

    public RecipePatchNoImage(String name, List<String> tags, List<String> steps, List<IngredientDisplay> ingredients) {
        this.name = name;
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

    public List<IngredientDisplay> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDisplay> ingredients) {
        this.ingredients = ingredients;
    }
}
