package com.example.beyourownbartender;

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

    @SerializedName("creationTime")
    OffsetDateTime creationTime;

    @SerializedName("modifiedTime")
    OffsetDateTime modifiedTime;

    @SerializedName("steps")
    List<String> steps;

    @SerializedName("tags")
    List<String> tags;
}
