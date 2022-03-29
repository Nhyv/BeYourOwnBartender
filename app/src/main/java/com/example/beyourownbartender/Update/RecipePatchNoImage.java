package com.example.beyourownbartender.Update;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePatchNoImage {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("rating")
    int rating;

    @SerializedName("steps")
    List<String> steps;

    @SerializedName("tags")
    List<String> tags;

    public RecipePatchNoImage(int id, String name, int rating, List<String> tags, List<String> steps) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.tags = tags;
        this.steps = steps;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
}
