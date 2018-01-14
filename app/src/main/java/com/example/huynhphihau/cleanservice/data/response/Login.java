package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phihau on 5/24/2017.
 */

public class Login {

    @SerializedName("access_token")
    String token;
    @SerializedName("token_type")
    String token_type;
    @SerializedName("data")
    User user;

    public String getToken() {
        return token;
    }

    public String getToken_type() {
        return token_type;
    }

    public User getUser() {
        return user;
    }
}