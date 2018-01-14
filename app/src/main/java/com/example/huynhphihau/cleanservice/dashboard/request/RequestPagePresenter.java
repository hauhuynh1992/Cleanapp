package com.example.huynhphihau.cleanservice.dashboard.request;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.data.response.BaseRespone;
import com.example.huynhphihau.cleanservice.data.response.Building;
import com.example.huynhphihau.cleanservice.data.response.BuildingDetail;
import com.example.huynhphihau.cleanservice.data.response.Company;
import com.example.huynhphihau.cleanservice.data.response.ReportOption;
import com.example.huynhphihau.cleanservice.data.response.RequestData;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;
import com.example.huynhphihau.cleanservice.util.AppLog;
import com.example.huynhphihau.cleanservice.util.BitmapUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phihau on 8/5/2017.
 */

public class RequestPagePresenter implements RequestPageContact.RequestPagePresenter {
    RequestPageActivity mView;
    private API mAPIService;

    private RequestData requestData;
    private List<Building> buildings;
    private BuildingDetail buildingDetail;
    private List<ReportOption> reportOptions;
    private List<Company> companies;
    private String path = "";

    public RequestPagePresenter(RequestPageActivity view) {
        this.mView = view;
        this.mAPIService = GetAPIService.getAPIService();
    }


    @Override
    public void getListBuilding() {
        mView.showProgressDialog();
        mAPIService.getListBuilding().enqueue(new Callback<BaseRespone<ArrayList<Building>>>() {
            @Override
            public void onResponse(Call<BaseRespone<ArrayList<Building>>> call, Response<BaseRespone<ArrayList<Building>>> response) {
                if (response.isSuccessful()) {
                    mView.hideProgressDialog();
                    buildings = response.body().getData();
                    mView.showListBuilding();
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<ArrayList<Building>>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public void getBuildingDetails(long id) {
        mView.showProgressDialog();
        mAPIService.getBuildingDetails(id).enqueue(new Callback<BaseRespone<BuildingDetail>>() {
            @Override
            public void onResponse(Call<BaseRespone<BuildingDetail>> call, Response<BaseRespone<BuildingDetail>> response) {
                if (response.isSuccessful()) {
                    mView.hideProgressDialog();
                    buildingDetail = response.body().getData();
                    mView.showListBuildingLevels();
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<BuildingDetail>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public void getReportOption() {
        mView.showProgressDialog();
        mAPIService.getReportOption().enqueue(new Callback<BaseRespone<ArrayList<ReportOption>>>() {
            @Override
            public void onResponse(Call<BaseRespone<ArrayList<ReportOption>>> call, Response<BaseRespone<ArrayList<ReportOption>>> response) {
                if (response.isSuccessful()) {
                    mView.hideProgressDialog();
                    reportOptions = response.body().getData();
                    mView.showListReportOptions();
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<ArrayList<ReportOption>>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });

    }

    @Override
    public void getListCompany() {
        mView.showProgressDialog();
        mAPIService.getCompany().enqueue(new Callback<BaseRespone<ArrayList<Company>>>() {
            @Override
            public void onResponse(Call<BaseRespone<ArrayList<Company>>> call, Response<BaseRespone<ArrayList<Company>>> response) {
                if (response.isSuccessful()) {
                    mView.hideProgressDialog();
                    companies = response.body().getData();
                    mView.showListCompanies();
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<ArrayList<Company>>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public void createReport(String title, long reportId, String remark, long buildingId, long buildingLevelId, long companyId, long jobType, String pathFile){
        File file = new File(pathFile);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part[] fileToUploads = new MultipartBody.Part[1];
        fileToUploads[0] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);

        RequestBody mTitle = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody mReportId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(reportId));
        RequestBody mRemark = RequestBody.create(MediaType.parse("text/plain"), remark);
        RequestBody mBuildingId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(buildingId));
        RequestBody mBuildingLevelId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(buildingLevelId));
        RequestBody mcompanyId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(companyId));
        RequestBody mjobType = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(jobType));

        if(TextUtils.isEmpty(title)){
            mView.showDialogMessage(mView.getString(R.string.msg_title_empty));
            return;
        }

        if(TextUtils.isEmpty(remark)){
            mView.showDialogMessage(mView.getString(R.string.msg_note_empty));
            return;
        }

        mView.showProgressDialog();
        mAPIService.createReport(mTitle, mReportId, mRemark, mBuildingId, mBuildingLevelId, mcompanyId, mjobType, fileToUploads).enqueue(new Callback<BaseRespone<RequestData>>() {
            @Override
            public void onResponse(Call<BaseRespone<RequestData>> call, Response<BaseRespone<RequestData>>response) {
                if (response.isSuccessful()) {
                    mView.hideProgressDialog();
                    mView.back();
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<RequestData>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }


    @Override
    public List<Company> getCompanies() {
        return companies;
    }

    @Override
    public List<Building> getBuilings() {
        return buildings;
    }

    @Override
    public BuildingDetail getBuilingDetail() {
        return buildingDetail;
    }

    @Override
    public List<ReportOption> getReportOptions() {
        return reportOptions;
    }

    @Override
    public void createTempFileWithSampleSize(Context context, String originPath) {
        // show progress bar
        mView.showProgressDialog();

        if (TextUtils.isEmpty(originPath)) {
            mView.showDialogMessage("Can not found Image");
            mView.hideProgressDialog();
            return;
        }

        // reduce image size
        Bitmap bitmap = BitmapUtils.decodeFile(originPath, 200);

        if (bitmap == null) {
            mView.showDialogMessage("Can not found Image");
            mView.hideProgressDialog();
            return;
        }

        AppLog.e("AAA", "BITMAP size " + bitmap.getByteCount());

        // get time
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());

        // create the temp file to upload
        File childFile = BitmapUtils.createImageFromBitmap(context, bitmap, "ValetAngles_" + timeStamp);

        if (childFile == null) {
            mView.showDialogMessage("Can not found Image");
            mView.hideProgressDialog();
            return;
        }
        // release bitmap
        bitmap = null;

        // upload temp file
        path = childFile.getAbsolutePath();
    }

    @Override
    public String getImagePath(){
        return path;
    }
}