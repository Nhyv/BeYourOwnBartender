package com.example.beyourownbartender;

public class Ban {

    @SerializedName("id")
    int id;

    @SerializedName("bannedUserId")
    int bannedUserId;

    @SerializedName("adminUserId")
    int adminUserId;

    @SerializedName("banLength")
    BanLength banLength;


    public Ban(int id, int bannedUserId, int adminUserId, BanLength banLength){
        this.id = id;
        this.bannedUserId = bannedUserId;
        this.adminUserId = adminUserId;
        this.banLength = banLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBannedUserId() {
        return bannedUserId;
    }

    public void setBannedUserId(int bannedUserId) {
        this.bannedUserId = bannedUserId;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public BanLength getBanLength() {
        return banLength;
    }

    public void setBanLength(BanLength banLength) {
        this.banLength = banLength;
    }
}
