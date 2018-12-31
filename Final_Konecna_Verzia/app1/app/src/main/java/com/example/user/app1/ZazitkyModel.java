package com.example.user.app1;

import com.google.gson.annotations.SerializedName;

public class ZazitkyModel {
    @SerializedName("ID")
    private int ID;
    @SerializedName("Images")
    private String images;

    public ZazitkyModel(int ID, String images) {
        this.ID = ID;
        this.images = images;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
