package com.example.huynhphihau.cleanservice.data.rest;

/**
 * Created by phihau on 5/24/2017.
 */

public class ServerConfig {


    public static final long CONNECTION_TIMEOUT = 1000L * 30;

    /* User API */
    // Login
    public static final String API_LOGIN = "/ws/v1/auth/login";

    // REGISTER
    public static final String API_REGISTER = "/ws/v1/auth/register";

    // API UPDATE PARTNER
    public static final String API_PARTNER = "/ws/v1/user";

    // API UPDATE AVATAR
    public static final String API_AVATAR = "/ws/v1/user/add-avatar";


    /*
    *
    * API FOR STANDARD AND SUPERVISOR
    *
    * */
    // API REQUEST HISTORY
    public static final String API_REQUEST_HISTORY = "/ws/v1/report";

    // API GET COMPANY
    public static final String API_GET_COMPANY = "/ws/v1/company";

    // API GET REPORT OPTION
    public static final String API_GET_REPORT_OPTION = "/ws/v1/report-option";

    // API GET BUILDING
    public static final String API_GET_LIST_BUILDING = "/ws/v1/building";

    // API GET BUILDING DETAILS
    public static final String API_GET_BUILDING_DETAILS = "/ws/v1/building/{id}";

    // API CREATE REPORT
    public static final String API_GET_CREATE_REPORT = "/ws/v1/report";

    // API RATING
    public static final String API_RATING = "/ws/v1/report/{id}/survey";



    /*
    *
    * API FOR SUPERVISOR
    *
    * */
    // API REPORT HISTORY
    public static final String API_SUPERVISOR_REPORT_HISTORY = "/ws/v1/supervisor/report";

    // API VIEW REPORT
    public static final String API_SUPERVISOR_VIEW_REPORT = "/ws/v1/supervisor/report/{id}";

    // API UPDATE REPORT STATUS
    public static final String API_SUPERVISOR_UPDATE_REPORT_STATUS = "/ws/v1/supervisor/report/{id}";

    // API UPDATE IMAGE AFTER REPORT
    public static final String API_SUPERVISOR_UPLOAD_IMAGE_AFTER = "ws/v1/supervisor/report/{id}/add-image";

    // API MARK READ
    public static final String API_SUPERVISOR_MARK_READ = "/ws/v1/supervisor/report/{id}/read";

    // API SURVEY
    public static final String API_SUBMIT_SURVEY = "/ws/v1/report/{id}/survey";
}
