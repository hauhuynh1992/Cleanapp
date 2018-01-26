package com.example.huynhphihau.cleanservice.base;

/**
 * Created by phihau on 5/30/2017.
 */

public class BaseConfig {

    public static final int NUM_LOAD_MORE = 10;

    public static final int NUM_ITEMS_ON_PAGE = 10;

    public static final int NUM_ITEMS_ON_DASHBOARD = 2;

    public static final int NUM_SKIP_ITEMS_ON_DASHBOARD = 0;


    public static final int REPORT_UNREAD = 0;

    public static final int REPORT_READ = 1;

    public static final int REPORT_UNRATE = 0;

    public static final int REPORT_RATE = 1;

    public static final int IMAGE_BEFORE = 1;

    public static final int IMAGE_AFTER = 0;



    /**
     * intent address
     */
    public static final String KEY_AUTHORIZATION = "Authorization";

    /**
     * REQUEST CODE
     */
    public static final int PERMISSIONS_REQUEST_CAMERA = 5;

    public static final int PERMISSIONS_REQUEST_GALLERY = 6;

    /**
     * USER ROLE
     */
    public static final String USER_STANDARD = "standard";

    public static final String USER_ADMIN = "admin";

    public static final String USER_SUPERVISOR = "supervisor";


    /**
     * REPORT STATUS
     */
    public static final int REP_STATUS_NEW = 10;                    // Outstanding

    public static final int REP_STATUS_PROCESSING = 11;

    public static final int REP_STATUS_COMPLETED = 12;              // Completed

    /**
     * FCM TYPE
     */
    public static final String FCM_TYPE_REP_NEW = "report_new";

    public static final String FCM_TYPE_REP_COMPLETE = "report_completed";

    /**
     * JOB TYPE
     */
    public static final int JOB_TYPE_INSPECTION = 1;

    public static final int JOB_TYPE_PERIODIC = 2;
}