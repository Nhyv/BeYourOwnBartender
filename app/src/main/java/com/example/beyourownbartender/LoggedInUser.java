package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

public class LoggedInUser {

    @SerializedName("userId")
    int userId;

    @SerializedName("creationDate")
    OffsetDateTime creationDate;

    @SerializedName("email")
    String email;

    @SerializedName("username")
    String username;

    public LoggedInUser(int userId, OffsetDateTime creationDate, String email, String username) {
        this.userId = userId;
        this.creationDate = creationDate;
        this.email = email;
        this.username = username;
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