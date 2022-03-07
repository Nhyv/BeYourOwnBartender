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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public OffsetDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(OffsetDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
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
