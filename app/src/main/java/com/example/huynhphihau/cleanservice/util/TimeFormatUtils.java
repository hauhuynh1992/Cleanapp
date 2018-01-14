package com.example.huynhphihau.cleanservice.util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by phihau on 5/29/2017.
 */

public class TimeFormatUtils {

    public static String formatDate(String origin) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return (new SimpleDateFormat("dd/MM/yyyy 'at' HH:mm a").format(simpleDateFormat.parse(origin.substring(0, 16))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
