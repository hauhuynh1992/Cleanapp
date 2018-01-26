package com.example.huynhphihau.cleanservice.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.example.huynhphihau.cleanservice.data.response.User;
import com.example.huynhphihau.cleanservice.util.SessionManager;

import io.fabric.sdk.android.Fabric;
import okhttp3.Headers;

/**
 * Created by LucLX on 4/22/17.
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    private SessionManager mSessionManager;

    // Checking if an Android application is running in the background
    private static boolean activityVisible;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mInstance = this;
        mSessionManager = new SessionManager(mInstance);
    }

    /*Manage Share Preference*/
    public void setUser(User userInfo) {
        mSessionManager.setUser(userInfo);
    }

    public void setTokenFromHeader(Headers headers) {
        mSessionManager.setTokenFromHeaders(headers);
    }


    public User getUser() {
        return mSessionManager.getUser();
    }

    public String getToken() {
        return mSessionManager.getToken();
    }

    public void setToken(String token) {
        mSessionManager.setToken(token);
    }

    public void clearSession() {
        mSessionManager.clearSession();
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    public String getType() {
        return mSessionManager.getType();
    }

    public void setType(String type) {
        mSessionManager.setType(type);
    }

    public void clearType() {
        mSessionManager.removeType();
    }

    public String getNumberUnRead() {
        return mSessionManager.getNumberUnRead();
    }

    public void setNumberUnRead(String num){
        mSessionManager.setNumberUnRead(num);
    }

    public void clearNumberUnRead() {
        mSessionManager.removeNumberUnRead();
    }
}
