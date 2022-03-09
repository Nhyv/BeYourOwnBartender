package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class LikeDisplay {
    @SerializedName("liked")
    boolean liked;

    @SerializedName("rating")
    int rating;

    public LikeDisplay(boolean liked, int rating) {
        this.liked = liked;
        this.rating = rating;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
