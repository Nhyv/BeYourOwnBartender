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

    @SerializedName("isAdmin")
    boolean isAdmin;

    public LoggedInUser(int userId, OffsetDateTime creationDate, String email, String username, boolean isAdmin) {
        this.userId = userId;
        this.creationDate = creationDate;
        this.email = email;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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