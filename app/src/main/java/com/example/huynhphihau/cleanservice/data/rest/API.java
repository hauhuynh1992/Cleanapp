package com.example.huynhphihau.cleanservice.data.rest;


import com.example.huynhphihau.cleanservice.data.response.BaseRespone;
import com.example.huynhphihau.cleanservice.data.response.Building;
import com.example.huynhphihau.cleanservice.data.response.BuildingDetail;
import com.example.huynhphihau.cleanservice.data.response.Company;
import com.example.huynhphihau.cleanservice.data.response.ListReport;
import com.example.huynhphihau.cleanservice.data.response.Login;
import com.example.huynhphihau.cleanservice.data.response.ReportData;
import com.example.huynhphihau.cleanservice.data.response.ReportOption;
import com.example.huynhphihau.cleanservice.data.response.RequestData;
import com.example.huynhphihau.cleanservice.data.response.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by phihau on 5/12/2017.
 */

public interface API {

    /**
     * PARRNER API
     */
    @POST(ServerConfig.API_LOGIN)
    @FormUrlEncoded
    Call<Login> submitAccount(@Field("username") String mobile_number,
                              @Field("password") String password,
                              @Field("device_id") String device_id,
                              @Field("device_token") String device_token,
                              @Field("device_type") String device_type);

    @POST(ServerConfig.API_REGISTER)
    @FormUrlEncoded
    Call<Object> registerAccount(@Field("firstName") String firstName,
                                 @Field("lastName") String lastName,
                                 @Field("username") String username,
                                 @Field("email") String email,
                                 @Field("password") String password,
                                 @Field("confirmPassword") String confirmPassword);

    @GET(ServerConfig.API_PARTNER)
    Call<BaseRespone<User>> loadUserInfo();

    @PUT(ServerConfig.API_PARTNER)
    @FormUrlEncoded
    Call<BaseRespone<User>> updatePassword(@Field("password") String newPassword,
                                           @Field("confirm_password") String confirmPassword);

    @PUT(ServerConfig.API_PARTNER)
    @FormUrlEncoded
    Call<BaseRespone<User>> updateEmail(@Field("email") String email_address);

    @PUT(ServerConfig.API_PARTNER)
    @FormUrlEncoded
    Call<BaseRespone<User>> updateName(@Field("full_name") String full_name);

//    @PUT(ServerConfig.API_UPDATE_PHONE)
//    @FormUrlEncoded
//    Observable<BaseRespone<User>> updatePhone(@Field("otp_number") String otp_number,
//                                              @Field("mobile_number") String mobile_number);

    @Multipart
    @POST(ServerConfig.API_AVATAR)
    Call<BaseRespone<User>> uploadAvatar(@Part MultipartBody.Part[] files);

    /**
     * REPORT API
     */
    @GET(ServerConfig.API_SUPERVISOR_REPORT_HISTORY)
    Call<ListReport> getHistoryReportOfSupervisor(@Query("skip") int limit, @Query("take") int page, @Query("status") int status);

    @GET(ServerConfig.API_SUPERVISOR_REPORT_HISTORY)
    Call<ListReport> getPeriodicHistory(@Query("skip") int limit, @Query("take") int page, @Query("job_type") int job_type);


    @GET(ServerConfig.API_SUPERVISOR_VIEW_REPORT)
    Call<BaseRespone<ReportData>> viewReport(@Path("id") long id);

    @PUT(ServerConfig.API_SUPERVISOR_UPDATE_REPORT_STATUS)
    @FormUrlEncoded
    Call<BaseRespone<Object>> updateReportStatus(@Path("id") long id,
                                                 @Field("status") int status);

    @POST(ServerConfig.API_RATING)
    @FormUrlEncoded
    Call<Object> rateReport(@Path("id") long id,
                            @Field("title") String title,
                            @Field("description") String description,
                            @Field("star_rated") float star_rated);

    @Multipart
    @POST(ServerConfig.API_SUPERVISOR_UPLOAD_IMAGE_AFTER)
    Call<BaseRespone<ReportData>> uploadImageAfter(
                                                    @Path("id") long id,
                                                    @Part("isBefore") int isBefore,
                                                    @Part MultipartBody.Part[] files);

    /**
     * REQUEST API
     */
    @GET(ServerConfig.API_REQUEST_HISTORY)
    Call<ListReport> getHistoryReportOfStandard(@Query("skip") int limit, @Query("take") int page, @Query("status") int status);

    @GET(ServerConfig.API_GET_COMPANY)
    Call<BaseRespone<ArrayList<Company>>> getCompany();

    @GET(ServerConfig.API_GET_REPORT_OPTION)
    Call<BaseRespone<ArrayList<ReportOption>>> getReportOption();

    @GET(ServerConfig.API_GET_LIST_BUILDING)
    Call<BaseRespone<ArrayList<Building>>> getListBuilding();

    @GET(ServerConfig.API_GET_BUILDING_DETAILS)
    Call<BaseRespone<BuildingDetail>> getBuildingDetails(@Path("id") long id);

    @Multipart
    @POST(ServerConfig.API_GET_CREATE_REPORT)
    Call<BaseRespone<RequestData>> createReport(@Part("title") RequestBody title
            , @Part("report_option_id") RequestBody report_option_id
            , @Part("remark") RequestBody remark
            , @Part("building_id") RequestBody building_id
            , @Part("building_level_id") RequestBody building_level_id
            , @Part("company_id") RequestBody company_id
            , @Part("job_type") RequestBody job_type
            , @Part MultipartBody.Part[] files);


    @GET(ServerConfig.API_SUPERVISOR_MARK_READ)
    Call<Object> markRead(@Path("id") long id);

    @POST(ServerConfig.API_RATING)
    @FormUrlEncoded
    Call<Object> submitSurvey(@Path("id") long id, @Field("title") String title,
                              @Field("description") String description,
                              @Field("star_rated") double star_rated);

}
