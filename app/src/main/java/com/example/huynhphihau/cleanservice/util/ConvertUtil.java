package com.example.huynhphihau.cleanservice.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by phihau on 6/22/2017.
 */

public class ConvertUtil {
    public static float convertDpToPixel(float dp, Context context) {
        if (context == null) {
            return 0f;
        }
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        if (context == null) {
            return 0f;
        }
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static float getDistanceTwoPoint(float aX, float aY, float bX, float bY) {
        return (float) Math.sqrt(Math.pow(aX - bX, 2) + Math.pow(aY - bY, 2));
    }
}
