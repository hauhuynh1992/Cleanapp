package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by huynhphihau on 8/29/17.
 */

public class Token {
    @SerializedName("token")
    String token;
    @SerializedName("refresh_token")
    String refresh_token;
    @SerializedName("token_type")
    String token_type;

    public String getToken_type() {
        return token_type;
    }

    public String getToken() {
        return token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }
}
