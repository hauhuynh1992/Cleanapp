package com.example.huynhphihau.cleanservice.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.data.response.User;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by phihau on 5/9/2017.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginSession";

    // Token
    public static final String KEY_TOKEN = "token";

    // Refresh Token
    public static final String KEY_REFRESH_TOKEN = "refreshToken";

    // User's information
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_PHOTO_URL = "photourl";
    public static final String KEY_MOBILE_NUMBER = "mobilephone";
    public static final String KEY_EMAIL_ADDRESS = "emailAddress";
    public static final String KEY_ROLES = "roles";

    // FCM Body
    public static final String TYPE = "type";

    // NUMBER UNREAD
    public static final String NUMBER_UNREAD = "numberUnRead";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void setUser(User userInfo) {

        AppLog.d("XXXX - setUser", userInfo.toString());
        StringBuilder roles = new StringBuilder();
        for (int i = 0; i < userInfo.getRoles().size(); i++) {
            roles.append(userInfo.getRoles().get(i)).append(",");
        }


        editor.putLong(KEY_ID, userInfo.getId());
        editor.putString(KEY_USER_NAME, userInfo.getUsername() == null ? "-" : userInfo.getUsername());
        editor.putString(KEY_FIRST_NAME, userInfo.getFirstName() == null ? "-" : userInfo.getFirstName());
        editor.putString(KEY_LAST_NAME, userInfo.getLastName() == null ? "-" : userInfo.getLastName());
        editor.putString(KEY_PHOTO_URL, userInfo.getPhotoUrl() == null ? "-" : userInfo.getPhotoUrl());
        editor.putString(KEY_MOBILE_NUMBER, userInfo.getPhone_number() == null ? "00000000" : userInfo.getPhone_number());
        editor.putString(KEY_ROLES, roles.toString() == null ? "-" : roles.toString());
        editor.putString(KEY_EMAIL_ADDRESS, userInfo.getEmail() == null ? "-" : userInfo.getEmail());
        editor.commit();
    }


    /**
     * Get Get User data
     */
    public User getUser() {
        User user = new User();

        String[] roles = pref.getString(KEY_ROLES,"-").split(",");
        ArrayList<String> listRole = new ArrayList<>(Arrays.asList(roles));

        user.setId(pref.getLong(KEY_ID, -1));
        user.setUsername(pref.getString(KEY_USER_NAME, "-"));
        user.setFirstName(pref.getString(KEY_FIRST_NAME, "-"));
        user.setLastName(pref.getString(KEY_LAST_NAME, "-"));
        user.setPhone_number(pref.getString(KEY_MOBILE_NUMBER, "00000000"));
        user.setPhotoUrl(pref.getString(KEY_PHOTO_URL, "-"));
        user.setRoles(listRole);
        user.setEmail(pref.getString(KEY_EMAIL_ADDRESS, "-"));

        AppLog.d("XXXX - getUser", user.toString());

        return user;
    }

    public void setTokenFromHeaders(okhttp3.Headers header) {

        String token = header.get(BaseConfig.KEY_AUTHORIZATION).toString();
        editor.putString(KEY_TOKEN, token);

        // commit changes
        editor.commit();
    }

    public void setToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    /**
     * Get Number Un-read
     */
    public String getNumberUnRead() {
        return pref.getString(NUMBER_UNREAD, "-");
    }

    /**
     * Set Number Un-read
     */
    public void setNumberUnRead(String num) {
        editor.putString(NUMBER_UNREAD, num);
        editor.commit();
    }

    /**
     * Remove Number Un-read
     */
    public void removeNumberUnRead() {
        editor.remove(NUMBER_UNREAD);
        editor.commit();
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    /**
     * Clear session details
     */
    public void clearSession() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Remove User
     */
    public void removeUser() {
        // Remove User's information
        editor.remove(KEY_ID);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_FIRST_NAME);
        editor.remove(KEY_LAST_NAME);
        editor.remove(KEY_MOBILE_NUMBER);
        editor.remove(KEY_EMAIL_ADDRESS);
        editor.remove(KEY_PHOTO_URL);
        editor.remove(KEY_ROLES);
        editor.commit();
    }

    public void setType(String type) {

        editor.putString(TYPE, type);
        // commit changes
        editor.commit();
    }

    public String getType() {
        return pref.getString(TYPE, "");
    }

    public void removeType() {
        // Remove Ferry ID
        editor.remove(TYPE);
        editor.commit();
    }
}