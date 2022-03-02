package com.example.beyourownbartender;

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
}
