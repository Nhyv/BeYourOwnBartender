package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class LikeDisplay {
    @SerializedName("liked")
    boolean liked;

    @SerializedName("ratingScore")
    int ratingScore;

    public LikeDisplay(boolean liked, int ratingScore) {
        this.liked = liked;
        this.ratingScore = ratingScore;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }
}
