package com.example.beyourownbartender;

import java.time.OffsetDateTime;

public class Image {

    @SerializedName("id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("creationTime")
    OffsetDateTime creationTime;

    public Image(int id, String title, OffsetDateTime creationTime) {
        this.id = id;
        this.title = title;
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
