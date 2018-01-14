package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huynhphihau on 12/18/17.
 */

public class Image {
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }
}