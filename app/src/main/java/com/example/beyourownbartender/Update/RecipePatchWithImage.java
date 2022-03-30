package com.example.beyourownbartender.Update;

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

    public RecipePatchWithImage(String name, String imageData, List<String> tags, List<String> steps) {
        this.name = name;
        this.imageData = imageData;
        this.tags = tags;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageData;
    }

    public void setImageUrl(String imageUrl) {
        this.imageData = imageUrl;
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
