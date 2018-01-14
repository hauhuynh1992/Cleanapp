package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by phihau on 5/24/2017.
 */

public class BaseRespone<T> {
    @SerializedName("data")
    T data;

    public T getData() {
        return data;
    }
}