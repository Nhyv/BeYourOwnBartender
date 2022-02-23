package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

public class Registration {
    @SerializedName("username")
    String username;

    @SerializedName("email")
    String email;

    @SerializedName("password")
    String password;

    public Registration(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
