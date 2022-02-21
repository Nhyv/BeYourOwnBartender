package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

public class LoggedInUser {

    @SerializedName("token")
    String accessToken;

    @SerializedName("refresh")
    String refreshToken;

    @SerializedName("userId")
    int userId;

    @SerializedName("creationDate")
    OffsetDateTime creationDate;

    @SerializedName("email")
    String email;

    @SerializedName("username")
    String username;

    public LoggedInUser(String accessToken, String refreshToken, int userId, OffsetDateTime creationDate, String email, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.creationDate = creationDate;
        this.email = email;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getUserId() {
        return userId;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}