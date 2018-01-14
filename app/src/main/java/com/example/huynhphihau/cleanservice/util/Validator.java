package com.example.huynhphihau.cleanservice.util;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by LucLX on 5/12/17.
 */

public class Validator {
    public final static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public final static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && Patterns.PHONE.matcher(phone).matches();
    }
}
