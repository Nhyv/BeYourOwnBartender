package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.util.List;

public class Recipe {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("rating")
    int rating;

    @SerializedName("authorId")
    int authorId;

    @SerializedName("imageId")
    int imageId;

    @SerializedName("creationTime")
    OffsetDateTime creationTime;

    @SerializedName("modifiedTime")
    OffsetDateTime modifiedTime;

    @SerializedName("steps")
    List<String> steps;

    @SerializedName("tags")
    List<String> tags;

    public Recipe(int id, String name, int rating, int authorId, int imageId, OffsetDateTime creationTime, OffsetDateTime modifiedTime, List<String> tags, List<String> steps) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.authorId = authorId;
        this.imageId = imageId;
        this.creationTime = creationTime;
        this.modifiedTime = modifiedTime;
        this.tags = tags;
        this.steps = steps;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getSteps() {
        return steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getAuthorid() {
        return authorId;
    }

    public int getImageId() {
        return imageId;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public OffsetDateTime getModifiedTime() {
        return modifiedTime;
    }
}
