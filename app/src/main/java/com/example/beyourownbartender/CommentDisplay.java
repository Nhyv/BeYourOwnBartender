package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

public class CommentDisplay {
    @SerializedName("id")
    int Id;

    @SerializedName("authorId")
    int authorId;

    @SerializedName("authorName")
    String authorName;

    @SerializedName("content")
    String content;

    @SerializedName("rating")
    int rating;

    @SerializedName("recipeId")
    int recipeId;

    @SerializedName("creationTime")
    OffsetDateTime creationTime;

    @SerializedName("modifiedTime")
    OffsetDateTime modifiedTime;

    public CommentDisplay(int id, int authorId, int rating, String authorName, String content,
                          int recipeId, OffsetDateTime creationTime, OffsetDateTime modifiedTime) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.content = content;
        this.recipeId = recipeId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public int getRecipeId() {
        return recipeId;
    }
}
