package com.example.huynhphihau.cleanservice.data.form;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LucLX on 5/9/17.
 */

public class ErrorResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
