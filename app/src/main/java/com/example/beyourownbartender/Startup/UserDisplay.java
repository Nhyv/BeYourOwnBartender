package com.example.beyourownbartender.Startup;

import com.google.gson.annotations.SerializedName;

public class UserDisplay {
    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;

    @SerializedName("id")
    int id;

    @SerializedName("isAdmin")
    boolean isAdmin;

    public UserDisplay(String username, String email, int id, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
