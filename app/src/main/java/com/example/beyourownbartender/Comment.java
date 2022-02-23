package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class Comment {
    @SerializedName("authorId")
    int authorId;

    @SerializedName("authorName")
    String authorName;

    @SerializedName("content")
    String content;

    @SerializedName("recipeId")
    int recipeId;

    public Comment(int authorId, String authorName, String content, int recipeId) {
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
