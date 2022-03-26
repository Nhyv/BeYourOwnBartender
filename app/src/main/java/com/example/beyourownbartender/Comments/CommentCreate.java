package com.example.beyourownbartender.Comments;

import com.google.gson.annotations.SerializedName;

public class CommentCreate {
    @SerializedName("authorId")
    int authorId;

    @SerializedName("authorName")
    String authorName;

    @SerializedName("content")
    String content;

    @SerializedName("recipeId")
    int recipeId;

    public CommentCreate(int authorId, String authorName, String content, int recipeId) {
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
