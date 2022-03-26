package com.example.beyourownbartender.Welcome;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.util.List;

public class RecipeCreate {
    @SerializedName("name")
    String name;

    @SerializedName("authorId")
    int authorId;

    @SerializedName("imageData")
    String imageData;

    @SerializedName("steps")
    List<String> steps;

    @SerializedName("tags")
    List<String> tags;

    public RecipeCreate(String name, int authorId, String imageData, List<String> steps, List<String> tags) {
        this.name = name;
        this.authorId = authorId;
        this.imageData = imageData;
        this.steps = steps;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
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

