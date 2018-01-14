package com.example.huynhphihau.cleanservice.data.rest;

import com.example.huynhphihau.cleanservice.BuildConfig;


/**
 * Created by phihau on 5/8/2017.
 */

public class GetAPIService {

    private GetAPIService() {
    }

    public static API getAPIService() {
        return GetClient.getClient(BuildConfig.BASE_URL).create(API.class);
    }
}
