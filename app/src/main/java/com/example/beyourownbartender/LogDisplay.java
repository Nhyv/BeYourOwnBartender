package com.example.beyourownbartender;

import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;

public class LogDisplay {
    @SerializedName("id")
    int id;

    @SerializedName("content")
    String content;

    @SerializedName("creationDate")
    OffsetDateTime creationDate;
}
