package com.example.huynhphihau.cleanservice.dashboard.report;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.data.response.BaseRespone;
import com.example.huynhphihau.cleanservice.data.response.ReportData;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;
import com.example.huynhphihau.cleanservice.util.AppLog;
import com.example.huynhphihau.cleanservice.util.BitmapUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phihau on 8/5/2017.
 */

public class ReportPagePresenter implements ReportPageContact.ReportPagePresenter {
    ReportPageActivity mView;
    private API mAPIService;
    private CompositeDisposable mCompositeDisposable;

    private ReportData reportData;

    public ReportPagePresenter(ReportPageActivity view) {
        this.mView = view;
        this.mAPIService = GetAPIService.getAPIService();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void updateReportStatus(final long id, int status) {
        mView.showProgressDialog();
        mAPIService.updateReportStatus(id, status).enqueue(new Callback<BaseRespone<Object>>() {
            @Override
            public void onResponse(Call<BaseRespone<Object>> call, Response<BaseRespone<Object>> response) {
                if (response.isSuccessful()) {
                    mView.hideProgressDialog();
                    viewReport(id);
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<Object>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public void viewReport(long id) {
        mView.showProgressDialog();
        mAPIService.viewReport(id).enqueue(new Callback<BaseRespone<ReportData>>() {
            @Override
            public void onResponse(Call<BaseRespone<ReportData>> call, Response<BaseRespone<ReportData>> response) {
                if (response.isSuccessful()) {
                    // Store job
                    reportData = response.body().getData();
                    handleReport();
                    mView.hideProgressDialog();

                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<ReportData>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public void markRead(long id) {
        mView.showProgressDialog();
        mAPIService.markRead(id).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    mView.hideProgressDialog();
                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public void createTempFileWithSampleSize(Context context, String originPath, long id) {
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
        File file = new File(childFile.getAbsolutePath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part[] fileToUploads = new MultipartBody.Part[1];
        fileToUploads[0] = MultipartBody.Part.createFormData("files", file.getName(), requestBody);

        mAPIService.uploadImageAfter(id, BaseConfig.IMAGE_AFTER, fileToUploads).enqueue(new Callback<BaseRespone<ReportData>>() {
            @Override
            public void onResponse(Call<BaseRespone<ReportData>> call, Response<BaseRespone<ReportData>> response) {
                if (response.isSuccessful()) {
                    viewReport(response.body().getData().getId());
                    // delete temp file
                    BitmapUtils.deleteImageTemp(context);
                    mView.hideProgressDialog();

                } else {
                    mView.hideProgressDialog();
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BaseRespone<ReportData>> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    @Override
    public ReportData getReport() {
        return reportData;
    }

    @Override
    public void submitRating(long id, String title, String remark, double rating) {

        mView.showProgressDialog();
        mAPIService.submitSurvey(id, title, remark, rating).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mView.hideProgressDialog();
                if (response.isSuccessful()) {
                    mView.back();
                } else {
                    mView.handleError(response.code(), response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mView.hideProgressDialog();
                mView.handleError(t);
            }
        });
    }

    private void handleReport() {
        if (this.reportData == null) {
            return;
        }

        //Get Job stauts
        int Status = reportData.getStatus();

        switch (Status) {
            case BaseConfig.REP_STATUS_PROCESSING:
            case BaseConfig.REP_STATUS_NEW:
                if (!BaseApplication.getInstance().getUser().isUserStandard())
                    mView.showCompleteButton();
                else mView.hideCompleteButton();
                break;
            case BaseConfig.REP_STATUS_COMPLETED:
                if (BaseApplication.getInstance().getUser().isUserStandard())
                    mView.showRattingStar();
                else mView.hideCompleteButton();
                break;
        }
    }

    @Override
    public void onStop() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}