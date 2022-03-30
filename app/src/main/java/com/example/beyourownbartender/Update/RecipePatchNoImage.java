package com.example.beyourownbartender.Update;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipePatchNoImage {
    @SerializedName("name")
    String name;

    @SerializedName("steps")
    List<String> steps;

    @SerializedName("tags")
    List<String> tags;

    public RecipePatchNoImage(String name, List<String> tags, List<String> steps) {
        this.name = name;
        this.tags = tags;
        this.steps = steps;
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
}
